<template>
  <v-card  class="mx-auto">
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
  </v-card>
</template>

<script>
export default {
    name: "sidebar_admin",

  data: () => ({
    menuItemList: [
      {
        title: "Admin",
        icon: "mdi-account",
        path: "/admin",
      },
      {
        title: "Food",
        icon: "mdi-food",
        path: "/admin/food",
      },
      {
        title: "Staff",
        icon: "mdi-account-supervisor ",
        path: "/admin/staff",
      },
      {
        title: "Order List",
        icon: "mdi-table",
        path: "/admin/order_list",
      },
    ],
  }),

  created() {},

  methods: {
    goToRoute(path) {
      // If Current Path is same with Clicked Path, No Go to Route
      if (this.$route.path != path) {
        this.$router.push({ path: path });
      }
    },
  },
}
</script>

<style scoped>

</style>