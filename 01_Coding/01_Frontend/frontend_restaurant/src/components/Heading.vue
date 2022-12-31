<template>
  <v-app-bar app color="pink lighten-1" dense dark>
    <a class="logoTitle" href="/">Restaurant Management</a>

    <v-spacer></v-spacer>

    <!-- Order List -->
    <!-- <span v-show="isLogin && !isAdmin">|</span> -->
    <router-link
      v-show="isLogin && loginUser.staffType == 'Staff'"
      class="mx-2 navlink"
      to="/login"
      >OrderList</router-link
    >

    <!-- Profile -->
    <span v-show="isLogin && loginUser.staffType == 'Staff'">|</span>
    <router-link
      v-if="isLogin && loginUser.staffType == 'Staff'"
      class="mx-2 navlink"
      to="/login"
      >Profile</router-link
    >

    <!-- Login out -->
    <span v-if="isLogin && loginUser.staffType == 'Staff'">|</span>
    <a v-if="isLogin" class="mx-2 navlink" @click="logout()">Logout</a>
  </v-app-bar>
</template>

<script>
export default {
  name: "heading",

  data: () => ({
    loginUser: {},
    isLogin: false,
    isStaff: false,
  }),

  created() {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal, oldVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );

    //isLogin
    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal, oldVal) => {
        this.isLogin = newVal;
      },
      {
        deep: true,
      }
    );

    //isAdmin
    this.isStaff = this.$store.state.isStaff;
    this.$store.watch(
      () => {
        return this.$store.state.isStaff;
      },
      (newVal, oldVal) => {
        this.isStaff = newVal;
      },
      {
        deep: true,
      }
    );
  },

  methods: {
    logout() {
      this.$store.commit("logout");
      if (this.$route.path != "/") {
        this.$router.push({ path: "/" });
      }
    },
  },
};
</script>

<style>
a.logoTitle {
  color: #fff !important;
  text-decoration: none;
  font-size: 24px;
  display: inline-block;
  margin-left: 30px;
}
.navlink {
  color: #fff !important;
  text-decoration: none;
}

.navlink:hover {
  cursor: pointer;
  text-decoration: underline;
}
</style>