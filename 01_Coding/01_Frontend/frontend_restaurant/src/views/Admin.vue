<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- Food Table -->
      <v-col cols="10">
        <h1 class="mb-3">Food List</h1>
        <!-- <v-row class="ma-0">
          <h1 class="mb-3">Food List</h1>
          <v-spacer></v-spacer>
          <v-btn icon @click="onClickCreateFood()">
            <v-icon size="30">mdi-plus-box</v-icon>
          </v-btn>
        </v-row> -->
        <v-data-table
          :headers="headers"
          :items="foodList"
          :items-per-page="5"
          class="elevation-1"
        >
          <!-- Image -->
          <template v-slot:[`item.image`]="{ item }">
            <v-img
              :src="localDomain + item.image"
              width="150"
              height="150"
              contain
            ></v-img>
          </template>

          <!-- Button -->
          <template v-slot:[`item.actions`]="{ item }">
            <!-- Update Btn -->
            <v-btn
              class="mr-2"
              color="primary"
              fab
              x-small
              elevation="2"
              @click="onClickUpdateFood(item)"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>

            <!-- Delete Btn -->
            <v-btn
              color="red"
              fab
              dark
              x-small
              elevation="2"
              @click="
                toDeleteFood = item;
                deleteDialog = true;
              "
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </v-col>
    </v-row>

    <!-- Delete Food Dialog -->
    <v-dialog v-model="deleteDialog" width="400">
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="pink lighten-1" dark>
          <div>Delete This Food Item?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="deleteDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <!-- Delete Info -->
        <v-card-text class="pa-4">
          <v-row dense>
            <v-col cols="3" class="font-weight-bold">ID</v-col>
            <v-col cols="7">{{ toDeleteFood.id }}</v-col>
            <v-col cols="3" class="font-weight-bold">FoodItem</v-col>
            <v-col cols="7">{{ toDeleteFood.foodItem }}</v-col>
          </v-row>
        </v-card-text>
        <!-- Delete Btn -->
        <v-card-actions class="justify-end">
          <v-btn color="red" dark @click="deleteFood()">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Update Food Dialog -->
    <v-dialog v-model="updateDialog" width="500">
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="pink lighten-1" dark>
          <div>Update This Food Item?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="updateDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <!-- Update Info -->
        <v-card-text class="pa-4">
          <v-form ref="foodUpdateForm" v-model="foodUpdateForm">
            <!-- Update Food Item -->
            <v-text-field
              v-model="toUpdateFood.foodItem"
              :counter="50"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 50) ||
                  'Title must be less than 50 characters',
              ]"
              label="FoodItem"
              required
            ></v-text-field>

            <!-- Update Price -->
            <v-text-field
              v-model="toUpdateFood.price"
              type="number"
              suffix="MMK"
              max="999999"
              min="0"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v >= 0 && v <= 999999) ||
                  'Price must be between 0 and 999999 MMK',
              ]"
              label="Price"
              required
            ></v-text-field>

            <!-- Update Stock -->
            <v-text-field
              v-model="toUpdateFood.stock"
              max="999999"
              min="0"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v >= 0 && v <= 999999) ||
                  'Price must be between 0 and 999999 MMK',
              ]"
              label="Stock"
              required
            ></v-text-field>

            <!-- Update Food Category -->
            <v-select
              v-model="toUpdateFood.category"
              :items="foodCategoryList"
              item-text="name"
              item-value="id"
              :rules="[(v) => !!v || 'Required']"
              label="Category"
              required
            ></v-select>

            <!-- Update Food Image -->
            <v-file-input
              v-model="toUpdateFood.imageEdit"
              label="Image"
              show-size
              prepend-icon="mdi-camera"
              placeholder="Choose Image"
              accept="image/png, image/jpeg"
              :rules="[
                (v) =>
                  !v ||
                  v.size < 10000000 ||
                  'Image size should be less than 10 MB!',
              ]"
              @change="onChangeImage"
            ></v-file-input>
            <!-- Image Preview -->
            <!-- Image is not seleced (null) / Original Image -->
            <v-img
              v-if="imagePreviewPath == null"
              :src="localDomain + toUpdateFood.image"
              width="200"
              height="150"
              contain
            >
            </v-img>
            <!-- Image is selected (not null) / Updated Image -->
            <v-img
              v-if="imagePreviewPath != null"
              :src="imagePreviewPath"
              width="200"
              height="150"
              contain
            ></v-img>

            <!-- Update Btn -->
            <v-btn
              :disabled="!foodUpdateForm"
              color="success"
              class="mr-4"
              @click="updateFood"
            >
              <span v-if="!loading">Update</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
            </v-btn>

            <!-- Error Alert -->
            <v-alert class="mt-3" v-show="errorAlert" dense type="error">
              Update Food Failed!
            </v-alert>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "../utils/utils";
import sidebar_admin from "../components/Sidebar_admin.vue";

export default {
  name: "admin_food_list",

  components: { sidebar_admin },

  data() {
    return {
      localDomain: utils.constant.localDomain,

      headers: [
        { text: "ID", value: "id", sortable: true },
        { text: "Image", value: "image", sortable: false },
        { text: "FoodItem", value: "foodItem", sortable: true },
        { text: "Price", value: "price", sortable: true },
        { text: "Stock", value: "stock", sortable: false },
        { text: "Category", value: "category.name", sortable: true },
        { text: "CreatedAt", value: "createdAt", sortable: true },
        { text: "UpdatedAt", value: "updatedAt", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      foodList: [],

      deleteDialog: false,
      toDeleteFood: {},

      updateDialog: false,
      foodUpdateForm: false,
      toUpdateFood: {
        foodItem: "",
        price: "",
        stock: "",
        category: 1,
        image: "",
        imageEdit: null,
      },
      imagePreviewPath: null,

      errorAlert: false,
      loading: false,

      foodCategoryList: [],
    };
  },

  async created() {
    await this.fetchFoodCategories();
    await this.fetchFoods();
  },

  methods: {
    async fetchFoods() {
      const resp = await utils.http.get("/admin/food");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.foodList = data;
        }
      }
    },

    async fetchFoodCategories() {
      const resp = await utils.http.get("/admin/category");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.foodCategoryList = data;
        }
      }
    },

    async deleteFood() {
      const resp = await utils.http.del(
        "/admin/food/delete/" + this.toDeleteFood.id
      );
      if (resp && resp.status === 200) {
        this.deleteDialog = false;
        // Refresh Food List
        await this.fetchFoods();
      } else {
        this.errorAlert = true;
      }
    },

    onClickUpdateFood(item) {
      this.updateDialog = true;
      //use Object.assign({}, item) instead of item
      //directly not to update in food list during fill the data in update form
      this.toUpdateFood = Object.assign({}, item);
      this.toUpdateFood.imageEdit = null;
      this.imagePreviewPath = null;
    },

    async updateFood() {
      //Form validation
      if (this.$refs.foodUpdateForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        //original image link
        let imagePath = this.toUpdateFood.image;

        //Null => Impage is not selected
        if (this.toUpdateFood.imageEdit != null) {
          //Update Image API
          const respImage = await utils.http.putMedia(
            "/admin/file/update",
            this.toUpdateFood.imageEdit,
            this.toUpdateFood.imageEdit.type,
            this.toUpdateFood.imagePath
          );
          if (respImage && respImage.status === 200) {
            imagePath = await respImage.text();
          } else {
            console.debug("Image Update Failed");
          }
        }

        //Update Food
        const respFood = await utils.http.put(
          "/admin/food/update/" + this.toUpdateFood.id,
          {
            foodItem: this.toUpdateFood.foodItem,
            price: this.toUpdateFood.price,
            stock: this.toUpdateFood.stock,
            category: {
              id: this.toUpdateFood.category,
            },
            image: imagePath,
          }
        );
        if (respFood && respFood.status === 200) {
          this.toUpdateFood.imageEdit = null;
          this.updateDialog = false;
          //refresh food list
          await this.fetchFoods();
        } else {
          this.errorAlert = true;
        }
        this.loading = false;
      }
    },

    onChangeImage(imageEdit) {
      this.imagePreviewPath = URL.createObjectURL(imageEdit);
    },
  },
};
</script>

<style>
</style>