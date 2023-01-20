<template>
  <div>
    <v-container>
      <v-form ref="loginForm" v-model="loginForm">
        <!--Staff ID -->
        <v-text-field
          v-model="staffID"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Staff ID must be less than 10 characters',
          ]"
          label="Staff ID"
          required
        ></v-text-field>

        <!-- Password -->
        <v-text-field
          v-model="password"
          :counter="10"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
          ]"
          :type="passwordShow ? 'text' : 'password'"
          :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow = !passwordShow"
          label="Password"
          required
        ></v-text-field>

        <!-- Login Btn -->
        <v-btn
          :disabled="!loginForm"
          color="success"
          class="mr-4"
          @click="login()"
        >
          <span v-if="!loading">Login</span>
          <v-progress-circular
            v-else
            indeterminate
            color="primary"
          ></v-progress-circular>
        </v-btn>

        <!-- Error Msg -->
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          Login Failed! <br />
          StaffID or Password is wrong!
        </v-alert>
      </v-form>
    </v-container>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "login",

  components: {},

  data() {
    return {
      loginForm: false,
      staffID: "",
      password: "",
      passwordShow: false,
      errorAlert: false,
      loading: false,
    };
  },

  async created() {},

  methods: {
    async login() {
      if (this.$refs.loginForm.validate()) {
        this.errorAlert = false;
        try {
          this.loading = true;

          //API call
          const resp = await utils.http.post("/login", {
            loginId: this.staffID,
            password: this.password,
          });
          if (resp && resp.status === 200) {
            const data = await resp.json();
            if (data) {
              this.$store.commit("setLoginUser", data);

              if (data.staffType == "Admin") {
                this.$router.push({ path: "/admin" });
              } else {
                this.$router.push({ path: "/home" });
              }
            }
          } else {
            this.errorAlert = true;
          }
        } catch (error) {
          console.log(error);
        } finally {
          this.loading = false;
        }
      }
    },
  },
};
</script>

<style>
</style>
