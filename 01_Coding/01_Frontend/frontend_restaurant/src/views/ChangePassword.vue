<template>
  <div>
    <v-container>
      <!-- form -->
      <form>
        <v-text-field v-show="false" v-model="ori_id" hidden></v-text-field>

        <!-- Current Password -->
        <v-text-field
          v-model="current_pwd"
          label="Current Password"
          :type="passwordShow1 ? 'text' : 'password'"
          :append-icon="passwordShow1 ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow1 = !passwordShow1"
          required
        ></v-text-field>

        <!-- New Password -->
        <v-text-field
          v-model="new_pwd"
          :counter="10"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
          ]"
          :type="passwordShow2 ? 'text' : 'password'"
          :append-icon="passwordShow2 ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow2 = !passwordShow2"
          label="New Password"
          required
        ></v-text-field>

        <!-- Confirm New Password -->
        <v-text-field
          v-model="con_new_pwd"
          :counter="10"
          :rules="[
            (v) => !!v || 'Required',
            (v) =>
              (v && v.length <= 10) ||
              'Password must be less than 10 characters',
          ]"
          :type="passwordShow3 ? 'text' : 'password'"
          :append-icon="passwordShow3 ? 'mdi-eye' : 'mdi-eye-off'"
          @click:append="passwordShow3 = !passwordShow3"
          label="Confirm New Password"
          required
        ></v-text-field>

        <!-- Submit Btn -->
        <v-btn
          class="mr-4"
          @click="submit"
          :disabled="con_new_pwd == '' || new_pwd == '' || current_pwd == ''"
          >Submit
        </v-btn>

        <!-- Clear Btn -->
        <v-btn @click="clear">Clear</v-btn>

        <!-- Error Alert -->
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          {{ errMsg }} <br
        /></v-alert>
      </form>
    </v-container>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "profile",

  components: {},

  data() {
    return {
      con_new_pwd: "",
      new_pwd: "",
      ori_id: "",
      current_pwd: "",
      passwordShow1: false,
      passwordShow2: false,
      passwordShow3: false,
      errorAlert: false,
      errMsg: "",
    };
  },

  async created() {
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
  },

  methods: {
    async submit() {
      const resp = await utils.http.post("/staff/changePwd", {
        ori_id: this.loginUser.id,
        current_pwd: this.current_pwd,
        new_pwd: this.new_pwd,
        con_new_pwd: this.con_new_pwd,
      });
      if (resp.status === 200) {
        this.$router.push({ path: "/home" });
      } else {
        this.errorAlert = true;
        const data = await resp.json();
        this.errMsg = data.message;
      }
    },

    clear() {
      this.current_pwd = "";
      this.new_pwd = "";
      this.con_new_pwd = "";
    },
  },
};
</script>

<style>
</style>