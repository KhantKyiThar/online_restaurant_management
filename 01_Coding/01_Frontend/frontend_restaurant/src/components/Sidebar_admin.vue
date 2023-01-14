<template>
  <v-card class="mx-auto">
    <v-navigation-drawer permanent>
      <v-list dense nav>
        <v-list-item
          v-for="item in menuItemList"
          :key="item.title"
          link
          @click="goToRoute(item.path)"
        >
          <!-- Icon -->
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <!-- title -->
          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <!-- <v-list>
      <v-list-item>
        <v-list-item-icon>
          <v-icon>mdi-account</v-icon>
        </v-list-item-icon>

        <v-list-item-title>Admin</v-list-item-title>
      </v-list-item>

      <v-list-group :value="true" prepend-icon="mdi-food">
        <template v-slot:activator>
          <v-list-item-title>Food</v-list-item-title>
        </template>

        <v-list-group
          :value="true"
          no-action
          sub-group
          v-for="(cat, index) in foodCategoryList"
          :key="index"
          link
          @click="onClickCategory(cat)"
        >
          <template v-slot:activator>
            <v-list-item-content>
              <v-list-item-title>{{ cat.name }}</v-list-item-title>
            </v-list-item-content>
          </template>
        </v-list-group>
      </v-list-group>
    </v-list> -->
  </v-card>
</template>

<script>
// import utils from "../utils/utils";
export default {
  name: "sidebar_admin",

  // components: {},

  // data() {
  //   return {
  //     localDomain: utils.constant.localDomain,

  //     catList: [],
  //   };
  // },

  data: () => ({
    menuItemList: [
      {
        title: "Admin",
        icon: "mdi-account",
        path: "/admin",
      },
      {
        title: "Food Create",
        icon: "mdi-food",
        path: "/admin/food_create",
      },
      {
        title: "Staff List",
        icon: "mdi-account-supervisor ",
        path: "/admin/staff_list",
      },
      {
        title: "Order List",
        icon: "mdi-table",
        path: "/admin/order_list",
      },
    ],
  }),

  // async created() {
  //   await this.fetchFoodCategories();
  // },

  // methods: {
  //   async fetchFoodCategories() {
  //     const resp = await utils.http.get("/admin/category");
  //     if (resp && resp.status === 200) {
  //       const data = await resp.json();
  //       // data is not undefined, not null
  //       if (data) {
  //         this.foodCategoryList = data;
  //       }
  //     }
  //   },

  //   async onClickCategory(cat) {
  //     const resp = await utils.http.get("/food/category" + cat.id);
  //     if (resp && resp.status === 200) {
  //       const data = await resp.json();
  //       if (data) {
  //         this.foodList = data;
  //       }
  //     }
  //   },
  // },

  methods: {
    goToRoute(path) {
      // If Current Path is same with Clicked Path, No Go to Route
      if (this.$route.path != path) {
        this.$router.push({ path: path });
      }
    },
  },
};
</script>

<style scoped>
</style>