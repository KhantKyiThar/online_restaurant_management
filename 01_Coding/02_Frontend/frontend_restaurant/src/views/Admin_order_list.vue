<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- Order List Table -->
      <v-col cols="10">
        <v-row class="ma-0">
          <h1 class="mb-3">Order List</h1>
        </v-row>
        <v-data-table
          :headers="headers"
          :items="orderList"
          :sort-by="`orderDate`"
          :sort-desc="true"
          class="elevation-2"
        >
          <template v-slot:[`item.details`]="{ item }">
            <v-btn
              color="pink lighten-1"
              fab
              text
              x-small
              @click="detailDialog(item)"
            >
              <v-icon>mdi-information</v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </v-col>
    </v-row>

    <!-- Detail Dialog -->
    <v-dialog v-model="dialog" width="500">
      <v-card>
        <v-card-title class="text-h5 pink lighten-1 white--text">
          Order Detail
        </v-card-title>
        <v-card-text>
          <v-data-table
            :headers="orderDetailHeaders"
            :items="orderDetail"
            hide-default-footer
            class="elevation-2 mt-2"
          >
            <template v-slot:[`item.total`]="{ item }">
              {{ eachTtlPrice(item) }}
            </template>
          </v-data-table>
        </v-card-text>

        <v-card-actions>
          <v-card-text>Total Price : {{ bill }}</v-card-text>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="dialog = false"> Close </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "../utils/utils";
import sidebar_admin from "../components/Sidebar_admin.vue";

export default {
  name: "Order",

  components: { sidebar_admin },

  data() {
    return {
      loginUser: [],
      orderList: [],
      orderDetail: [],
      bill: 0,
      dialog: false,
      headers: [
        { text: "Order Id", sortable: false, value: "id" },
        { text: "Staff Name", value: "staff.name" },
        { text: "Staff Id", value: "staff.id" },
        { text: "Total Price", value: "totalPrice" },
        { text: "Order Date", value: "orderDate" },
        { text: "Details", value: "details" },
      ],
      orderDetailHeaders: [
        { text: "Food", value: "food.foodItem" },
        { text: "Price", value: "food.price" },
        { text: "Qty", value: "foodCount" },
        { text: "Total", value: "total" },
      ],
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
    await this.fetchFoodOrder();
  },
  methods: {
    async fetchFoodOrder() {
      const resp = await utils.http.get("/admin/order");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.orderList = data;
        }
      }
    },
    async detailDialog(item) {
      this.dialog = true;
      this.bill = item.totalPrice;
      const resp = await utils.http.get("/admin/order/detail/" + item.id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.orderDetail = data;
        }
      }
    },
    eachTtlPrice(item) {
      return item.food.price * item.foodCount;
    },
  },
};
</script>

<style>
</style>