<template>
  <v-app>
    <v-app-bar color="deep-purple accent-4" dark absolute>
      <v-toolbar-title>Restaurant Management</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn text>
      Logout
    </v-btn>
    </v-app-bar>
    <v-main>
      <router-view/>
    </v-main>
    <v-footer color="deep-purple accent-4" dark padless>
      <v-col class="text-center" cols="12">
        <strong>Team GigaBytes</strong>
      </v-col>
  </v-footer> 
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
