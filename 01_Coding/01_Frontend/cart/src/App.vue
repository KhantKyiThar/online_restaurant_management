<template>
  <v-app>
    <v-app-bar app color="primary" dark> <v-spacer></v-spacer>about </v-app-bar>
    <v-main>
      <router-view class="mx-3 my-2" style="height: 100%" />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",

  data: () => ({
    //
  }),
};
</script>
