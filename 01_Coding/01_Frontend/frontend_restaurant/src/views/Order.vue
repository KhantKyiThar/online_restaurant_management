<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="orderList"
      :sort-by="`orderDate`"
      :sort-desc="true"
      class="elevation-2"
    >
      <template v-slot:[`item.details`]="{ item }">
        <v-btn
          class="mr-2"
          color="primary"
          fab
          x-small
          elevation="2"
          @click="detailDialog(item)"
        >
          <v-icon>mdi-pencil</v-icon>
        </v-btn>
      </template>
    </v-data-table>
    <v-dialog v-model="dialog" width="500">
      <v-card>
        <v-card-title class="text-h5 grey lighten-2">
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
import order from "../mock/order.json";

export default {
  name: "Order",
  components: {},
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
      const resp = await utils.http.get("/staff/order/" + this.loginUser.id);
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
      const resp = await utils.http.get("/staff/order/detail/" + item.id);
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