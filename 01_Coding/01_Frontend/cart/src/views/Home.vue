<template>
  <div>
    <v-row>
      <!-- Category Menu -->
      <v-col cols="2">
        <v-card class="mx-auto">
          <v-navigation-drawer permanent>
            <v-list dense nav>
              <v-list-item>
                <v-list-item-content>
                  Category
                  <v-list-item-title>All</v-list-item-title>
                  <v-list-item-title v-for="(c, i) in category" :key="i">{{
                    c.name
                  }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-navigation-drawer>
        </v-card>
      </v-col>
      <!-- Foot List -->
      <v-col cols="7">
        <v-row>
          <v-col cols="3" v-for="(p, i) in product" :key="i">
            <v-card class="mx-auto">
              <v-img :src="p.image" height="100" cover></v-img>
              <v-card-text>
                <div class="text-h5">{{ p.foodItem }}</div>
                <div class="text-body-1">{{ p.price }} Kyat</div>
                <div class="text-body-1">{{ p.stock }}</div>
                <div class="text-body-1 mb-1">
                  <v-btn @click="addToCard(p)">Add To Cart</v-btn>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <!-- Cart List-->
      <v-col cols="3">
        <!-- <v-card class="mx-auto">
          <v-navigation-drawer permanent>
            <v-list dense nav>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title v-for="(p, i) in cart" :key="i"
                    >{{ p.foodItem }} {{ p.qty }}
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-navigation-drawer>
        </v-card> -->
        <v-data-table
          :headers="headers"
          :items="desserts"
          hide-default-footer
          class="elevation-1"
        ></v-data-table>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import product from "../mock/product.json";
import category from "../mock/category.json";

export default {
  name: "Home",
  components: { product, category },
  data() {
    return {
      product: product,
      category: category,
      cart: [],
      headers: [
        {
          text: "Food",
          align: "start",
          value: "food",
        },
        { text: "Price", value: "price" },
        { text: "Qty", value: "qty" },
        { text: "Total Price", value: "totalPrice" },
      ],
    };
  },
  methods: {
    async addToCard(p) {
      var cartList = this.cart;
      var isInCart = cartList.find((v) => {
        return v.foodItem == p.foodItem;
      });
      if (isInCart) {
        isInCart.qty++;
      } else {
        this.cart.push({ ...p, qty: 1 });
      }
      console.log(this.cart);
    },
  },
};
</script>
