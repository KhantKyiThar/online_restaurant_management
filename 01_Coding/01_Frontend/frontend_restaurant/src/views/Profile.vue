<template>
  <div>
    <!-- Profile Card -->
    <v-card class="mx-auto pa-3 mt-10" max-width="344">
      <v-card-text>
        <div>Welcome</div>
        <p class="text-h4 text--primary">
          {{ loginUser.name }}
        </p>

        <p>Data</p>
        <div class="text--primary">
          <div>{{ loginUser.loginId }}</div>
          <div>Phone Number: {{ loginUser.phone }}</div>
          <div>Gender: {{ loginUser.gender }}</div>
          <div>Joining Date: {{ loginUser.joinedDate }}</div>
        </div>
      </v-card-text>

      <!-- Change Pwd Btn -->
      <v-btn text color="teal accent-4" @click="changePwd()">
        Change Password
      </v-btn>
    </v-card>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "Profile",
  components: {},
  data() {
    return {
      loginUser: {},
    };
  },

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
  },

  methods: {
    changePwd() {
      this.$router.push({ path: "/changePwd" });
    },
  },
};
</script>