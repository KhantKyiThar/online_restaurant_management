import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    loginUser: {},
    isLogin: false,
    isStaff: false,
  },

  getters: {
    loginUser: (state) => {
      let loginUser = sessionStorage.getItem("loginUser");
      if (loginUser != null) {
        state.loginUser = JSON.parse(loginUser);
        return JSON.parse(loginUser);
      } else {
        return state.loginUser;
      }
    },
    isLogin: (state) => {
      let isLogin = sessionStorage.getItem("isLogin");
      if (isLogin != null) {
        state.isLogin = isLogin;
        return isLogin;
      } else {
        return state.isLogin;
      }
    },
    isStaff: (state) => {
      let isStaff = sessionStorage.getItem("isStaff");
      if (isStaff != null) {
        state.isStaff = isStaff;
        return isStaff;
      } else {
        return state.isStaff;
      }
    },
  },

  mutations: {
    setLoginUser(state, user) {
      sessionStorage.setItem("loginUser", JSON.stringify(user));
      sessionStorage.setItem("isLogin", true);
      // sessionStorage.setItem("isStaff", true);
      state.loginUser = user;
      state.isLogin = true;
    },
    logout(state) {
      sessionStorage.removeItem("loginUser");
      sessionStorage.removeItem("isLogin");
      sessionStorage.removeItem("isStaff");
      state.loginUser = {};
      state.isLogin = false;
    },
  },
  actions: {
  },
  modules: {
  }
})
