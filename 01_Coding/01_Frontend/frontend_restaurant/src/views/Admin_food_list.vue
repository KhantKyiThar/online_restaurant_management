<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- Food Table -->
      <v-col cols="10">
        <v-data-table
          :headers="headers"
          :items="foodList"
          :items-per-page="5"
          class="elevation-1"
        >
          <!-- Image -->
          <template v-slot:[`item.imagePath`]="{ item }">
            <v-img
              :src="localDomain + item.imagePath"
              width="150"
              height="150"
              contain
            ></v-img>
          </template>

          <!-- Button -->
          <template v-slot:[`item.actions`]="{ item }">
            <!-- Update Btn -->
            <v-btn
              class="mr-2"
              color="primary"
              fab
              x-small
              elevation="2"
              @click="onClickUpdateFood(item)"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>

            <!-- Delete Btn -->
            <v-btn
              color="red"
              fab
              dark
              x-small
              elevation="2"
              @click="
                toDeleteMovie = item;
                deleteDialog = true;
              "
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import utils from "../utils/utils";
import sidebar_admin from "../components/Sidebar_admin.vue";

export default {};
</script>

<style>
</style>