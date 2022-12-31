<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>
      <v-col class="text-center">
        <v-text-title class="text-h3">
             Admin Page
        </v-text-title>
      </v-col>
      
    </v-row>
   
  </div>
</template>

<script>
import utils from "../utils/utils";
import sidebar_admin from "../components/Sidebar_admin.vue";

export default {
    name: "admin",

    components: { sidebar_admin },


}
</script>

<style>

</style>