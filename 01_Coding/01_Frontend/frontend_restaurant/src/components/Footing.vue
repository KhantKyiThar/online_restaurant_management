<template>
  <v-footer color="pink lighten-1" padless>
    <v-row justify="center" no-gutters>
      <!-- <v-btn
        v-for="link in links"
        :key="link"
        color="white"
        text
        rounded
        class="my-2"
      >
        {{ link }}
      </v-btn> -->
      <v-col class="pink lighten-1 py-4 text-center white--text" cols="12">
        <strong>Copy right @ </strong>{{ new Date().getFullYear() }} —
        <strong>1 Gigabyte Team</strong>
      </v-col>
    </v-row>
  </v-footer>
</template>

<script>
export default {
  name: "footing",

  data: () => ({
    // links: ["Home", "About Us", "Category", "Contact Us"],
  }),
};
</script>

<style scoped>
.text-white {
  color: white !important;
}
</style>