<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- Create Food Form -->
      <v-col cols="10">
        <v-form class="px-10" ref="foodCreateForm" v-model="foodCreateForm">
          <!-- Create Food Item -->
          <v-text-field
            v-model="foodItem"
            :counter="50"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v.length <= 50) ||
                'Food Item must be less than 50 characters',
            ]"
            label="Title"
            required
          ></v-text-field>

          <!-- Create Price -->
          <v-text-field
            v-model="price"
            type="number"
            suffix="MMK"
            max="999999"
            min="1"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v >= 0 && v <= 999999) ||
                'Price must be between 0 and 999999 MMK',
            ]"
            label="Price"
            required
          ></v-text-field>

          <!-- Create Stock -->
          <v-text-field
            v-model="stock"
            type="number"
            suffix="MMK"
            max="999999"
            min="1"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                (v && v > 0 && v <= 999999) ||
                'Stock must be between 0 and 999999 MMK',
            ]"
            label="Stock"
            required
          ></v-text-field>

          <!-- Category -->
          <v-select
            v-model="category"
            :items="FoodCategoryList"
            item-text="name"
            item-value="id"
            :rules="[(v) => !!v || 'Required']"
            label="Category"
            required
          >
          </v-select>

          <!-- Create Image -->
          <v-file-input
            v-model="imageCreate"
            label="Image"
            show-size
            prepend-icon="mdi-camera"
            placeholder="Choose Image"
            accept="image/png, image/jpeg"
            :rules="[
              (v) => !!v || 'Required',
              (v) =>
                !v ||
                v.size < 10000000 ||
                'Image size should be less than 10 MB!',
            ]"
            @change="onChangeImage"
          ></v-file-input>

          <!-- Poster Preview -->
          <v-img
            v-if="imagePreviewPath != null"
            :src="imagePreviewPath"
            width="200"
            height="150"
            contain
          >
          </v-img>

          <!-- Create Btn -->
          <v-btn
            :disabled="!foodCreateForm"
            color="success"
            class="mt-4 mr-4"
            @click="createFood()"
          >
            <span v-if="!loading">Create Food</span>
            <v-progress-circular
              v-else
              indeterminate
              color="primary"
            ></v-progress-circular>
          </v-btn>

          <!-- Error Alert For Movie -->
          <v-alert class="mt-3" v-show="errorAlert" dense type="error">
            Create Food Failed!
          </v-alert>
        </v-form>
      </v-col>
    </v-row>
  </div>
</template>
  
  <script>
import sidebar_admin from "../components/Sidebar_admin.vue";
import utils from "../utils/utils";

export default {
  name: "admin_create_food",
  components: { sidebar_admin },

  data() {
    return {
      foodCreateForm: false,

      foodItem: "",
      price: 0,
      stock: 0,
      category: 1,

      errorAlert: false,

      loading: false,

      imageCreate: null,
      imagePreviewPath: null,

      FoodCategoryList: [],
    };
  },

  async created() {
    await this.fetchFoodCategories();
  },

  methods: {
    async fetchFoodCategories() {
      const resp = await utils.http.get("/category");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.FoodCategoryList = data;
        }
      }
    },

    async createFood() {
      // Form Validation
      if (this.$refs.foodCreateForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        if (this.imageCreate != null) {
          let respImageData = null;

          //Image
          const respImage = await utils.http.postMedia(
            "/admin/file/create",
            this.imageCreate,
            this.imageCreate.type
          );
          if (respImage.status === 200) {
            respImageData = await respImage.text();
          } else {
            this.errorAlert = true;
          }

          //  Create Food

          if (respImageData) {
            // Create Food API
            const respFood = await utils.http.post("/admin/food/create", {
              foodItem: this.foodItem,
              price: this.price,
              stock: this.stock,
              category: {
                id: this.category,
              },
              image: respImageData,
            });
            if (respFood && respFood.status === 200) {
              this.$router.push({ path: "/admin" });
            } else {
              this.errorAlert = true;
            }
          }
        }

        this.loading = false;
      }
    },

    onChangeImage(imageCreate) {
      this.imagePreviewPath = URL.createObjectURL(imageCreate);
    },
  },
};
</script>
  