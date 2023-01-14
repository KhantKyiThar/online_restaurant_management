// import Vue, { onRenderTracked } from 'vue';
import Vue from "vue";
import VueRouter from "vue-router";
import store from "../store";

import login from "../views/Login.vue";
import home from "../views/Home.vue";
import order from "../views/Order.vue";
import profile from "../views/Profile.vue";
import checkout from "../views/Checkout.vue";
import about from "../views/About.vue";
import contact from "../views/Contact.vue";

import admin from "../views/Admin.vue";
import admin_food_create from "../views/Admin_food_create.vue";
import admin_staff_list from "../views/Admin_staff_list.vue";
import admin_order_list from "../views/Admin_order_list.vue";

Vue.use(VueRouter)

const routes = [
  {
    path: "/",
    name: "login",
    component: login
  },
  {
    path: "/home",
    name: "home",
    component: home,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/order",
    name: "order",
    component: order,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/profile",
    name: "profile",
    component: profile,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/checkout",
    name: "checkout",
    component: checkout,
    meta: {
      requiresAuth: true,
    },
  },
  {
    path: "/about",
    name: "about",
    component: about,
  },
  {
    path: "/contact",
    name: "contact",
    component: contact,
  },

  // Admin
  {
    path: "/admin",
    name: "admin",
    component: admin,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/food_create",
    name: "admin_food_create",
    component: admin_food_create,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/staff_list",
    name: "admin_staff_list",
    component: admin_staff_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
  {
    path: "/admin/order_list",
    name: "admin_order_list",
    component: admin_order_list,
    meta: {
      requiresAuth: true,
      requiresAdmin: true,
    },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  let loginUser = router.app.$store.getters.loginUser;
  let isLogin = router.app.$store.getters.isLogin;

  // from - login
  // to - Home
  // Need to be login, But is not login
  if (to.meta.requiresAuth == true && !isLogin) {
    next({ path: "/" });
  }

  // Need to be login, Need to be admin
  else if (
    to.meta.requiresAuth == true &&
    to.meta.requiresAdmin == true &&
    loginUser.staffType != "Admin"
  ) {
    next({ path: "/home" });
  }

  // If All Okay
  else {
    next();
  }
});

export default router;
