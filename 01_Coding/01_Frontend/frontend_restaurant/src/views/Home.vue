<template>
  <div>
    <v-row>
      <!-- Category Menu -->
      <v-col cols="2">
        <v-card class="mx-auto">
          <v-navigation-drawer permanent>
            <v-list dense nav>
              <!-- <v-list-item>
                <v-list-item-content>
                  Category
                  <v-list-item-title class="cat" @click="showAllFood()">
                    All
                  </v-list-item-title>
                  <v-list-item-title
                    class="cat"
                    v-for="(c, i) in category"
                    :key="i"
                    @click="showByCat(c)"
                    >{{ c.name }}
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item> -->
              <v-list-item-title> Category </v-list-item-title>
              <v-list-item>
                <v-list-item-content>
                  <v-list-item-title class="cat" @click="showAllFood()">
                    All
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
              <v-list-item
                v-for="(c, i) in category"
                :key="i"
                @click="showByCat(c)"
              >
                <v-list-item-content>
                  <v-list-item-title>{{ c.name }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-navigation-drawer>
        </v-card>
      </v-col>
      <!-- Foot List -->
      <v-col cols="6">
        <!-- Show All-->
        <v-row v-if="toShowAllFood">
          <v-col cols="4" v-for="(p, i) in product" :key="i">
            <v-card class="mx-auto">
              <v-img :src="localDomain + p.image" height="100" cover></v-img>
              <v-card-text>
                <div class="text-h5">{{ p.foodItem }}</div>
                <div class="text-body-1">Price : {{ p.price }} Kyat</div>
                <div class="text-body-1">Stock : {{ p.stock }} Unit</div>
                <div class="text-body-1 mb-1">
                  <v-btn @click="addToCard(p)" :disabled="p.stock <= 0">
                    Add To Cart
                    <v-icon color="pink lighten-1" small class="ml-1">
                      mdi-cart
                    </v-icon>
                  </v-btn>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
        <!-- Show By Category Id -->
        <v-row v-else>
          <v-col
            cols="4"
            v-for="(p, i) in product"
            :key="i"
            v-show="p.category.id == catId"
          >
            <v-card class="mx-auto">
              <v-img :src="localDomain + p.image" height="100" cover></v-img>
              <v-card-text>
                <div class="text-h5">{{ p.foodItem }}</div>
                <div class="text-body-1">Price : {{ p.price }} Kyat</div>
                <div class="text-body-1">Stock : {{ p.stock }} Unit</div>
                <div class="text-body-1 mb-1">
                  <v-btn @click="addToCard(p)" :disabled="p.stock <= 0">
                    Add To Cart
                    <v-icon color="pink lighten-1" small class="ml-1">
                      mdi-cart
                    </v-icon>
                  </v-btn>
                </div>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <!-- Cart List-->
      <v-col cols="4">
        <v-data-table
          :headers="headers"
          :items="cart"
          hide-default-footer
          class="elevation-2"
        >
          <template v-slot:[`item.qty`]="{ item }">
            <v-icon
              class="mr-1"
              color="pink lighten-1"
              fab
              text
              x-small
              @click="increase(item)"
              v-show="item.stock > 0"
              >mdi-plus
            </v-icon>
            {{ item.qty }}
            <v-icon
              class="ml-1"
              color="pink lighten-1"
              fab
              text
              x-small
              @click="reduce(item)"
              >mdi-minus
            </v-icon>
          </template>
          <template v-slot:[`item.total`]="{ item }">
            {{ item.total }}
            <v-icon
              color="pink lighten-1"
              @click="remove(item)"
              small
              class="ml-5"
            >
              mdi-delete
            </v-icon>
          </template>
          <template slot="body.append">
            <tr v-show="this.cart.length > 0">
              <!-- <td><span class="cat" @click="checkout()">Checkout</span></td> -->
              <td><span class="cat" @click="dialog = true">Checkout</span></td>
              <td></td>
              <td>Total Price</td>
              <td>
                {{ calBill }}
                <v-icon
                  color="pink lighten-1"
                  @click="cancel()"
                  small
                  class="ml-5"
                >
                  mdi-delete
                </v-icon>
              </td>
            </tr>
          </template>
        </v-data-table>
        <v-row>
          <div
            class="pink--text lighten-2 mt-5 ml-5"
            v-show="this.cart.length == 0 && orderSuccess"
          >
            Order Success !!!!
          </div>
        </v-row>
      </v-col>
      <v-dialog v-model="dialog" width="500">
        <v-card>
          <v-card-title class="text-h5 pink lighten-1 white--text">
            Do you want to Checkout?
          </v-card-title>
          <v-card-text>
            <v-data-table
              :headers="headers"
              :items="cart"
              hide-default-footer
              class="elevation-2 mt-2"
            >
            </v-data-table>
          </v-card-text>
          <v-card-actions>
            <v-card-text>Total Price : {{ bill }}</v-card-text>
            <v-spacer></v-spacer>
            <v-btn
              color="pink lighten-1"
              text
              @click="(dialog = false), checkout()"
            >
              Checkout
            </v-btn>
            <v-btn color="pink lighten-1" text @click="dialog = false">
              Close
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>


<script>
import utils from "../utils/utils";

export default {
  name: "Home",
  components: {},
  data() {
    return {
      localDomain: utils.constant.localDomain,

      dialog: false,
      orderSuccess: false,
      product: [],
      category: [],
      cart: [],
      toShowAllFood: true,
      bill: 0,
      catId: 0,
      loginUser: [],
      headers: [
        { text: "Food", value: "foodItem" },
        { text: "Price", value: "price" },
        { text: "Qty", value: "qty" },
        { text: "Total", value: "total" },
      ],
    };
  },

  async created() {
    await this.fetchCategory();
    await this.fetchFood();
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
    async fetchCategory() {
      const resp = await utils.http.get("/category");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.category = data;
        }
      }
    },
    async fetchFood() {
      const resp = await utils.http.get("/food");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.product = data;
        }
      }
    },
    async showAllFood() {
      this.toShowAllFood = true;
    },
    async showByCat(cat) {
      this.toShowAllFood = false;
      this.catId = cat.id;
    },
    async addToCard(p) {
      var cartList = this.cart;
      var productList = this.product;

      var isInProduct = productList.find((v) => {
        return v.id == p.id;
      });
      var isInCart = cartList.find((v) => {
        return v.id == p.id;
      });

      isInProduct.stock--;
      if (isInCart) {
        isInCart.stock--;
        isInCart.qty++;
        isInCart.total = isInCart.qty * isInCart.price;
      } else {
        this.cart.push({
          ...p,
          qty: 1,
          total: p.price,
        });
      }
    },
    async increase(item) {
      var cartList = this.cart;
      var productList = this.product;

      var isInProduct = productList.find((v) => {
        return v.id == item.id;
      });
      var isInCart = cartList.find((v) => {
        return v.id == item.id;
      });

      isInProduct.stock--;
      isInCart.stock--;
      isInCart.qty++;
      isInCart.total = isInCart.qty * isInCart.price;
    },
    async reduce(item) {
      var cartList = this.cart;
      var productList = this.product;

      var isInProduct = productList.find((v) => {
        return v.id == item.id;
      });
      var isInCart = cartList.find((v) => {
        return v.id == item.id;
      });

      isInProduct.stock++;
      isInCart.stock++;
      isInCart.qty--;
      isInCart.total = isInCart.qty * isInCart.price;

      if (isInCart.qty == 0) {
        this.remove(item);
      }
    },
    async remove(item) {
      var cartList = this.cart;
      var productList = this.product;

      var isInProduct = productList.find((v) => {
        return v.id == item.id;
      });
      var isInCart = cartList.find((v) => {
        return v.id == item.id;
      });

      isInProduct.stock = isInProduct.stock + isInCart.qty;
      var index = this.cart.indexOf(isInCart);
      this.cart.splice(index, 1);
    },
    async cancel() {
      var length = this.cart.length;
      for (var i = 0; i < length; i++) {
        var isInProduct = this.product.find((v) => {
          return v.id == this.cart[i].id;
        });
        isInProduct.stock = isInProduct.stock + this.cart[i].qty;
      }
      this.cart.splice(0, length);
    },
    async checkout() {
      var length = this.cart.length;

      //Create Order
      const resp = await utils.http.post("/staff/order/add", {
        totalPrice: this.bill,
        staff: this.loginUser,
      });
      if (resp && resp.status === 200) {
        this.orderSuccess = true;
        var orderData = await resp.json();
      }

      //Update Food stock and Create Order Detail
      for (var i = 0; i < length; i++) {
        try {
          var isInProduct = this.product.find((v) => {
            return v.id == this.cart[i].id;
          });

          //Update Food Stock
          await utils.http.put("/staff/food/update/" + isInProduct.id, {
            foodItem: isInProduct.foodItem,
            image: isInProduct.image,
            price: isInProduct.price,
            stock: isInProduct.stock,
            category: isInProduct.category,
          });

          //Create Order Detail
          await utils.http.post("/staff/order/detail", {
            food: isInProduct,
            foodOrder: orderData,
            foodCount: this.cart[i].qty,
          });
        } catch (error) {
          console.log(error);
        }
      }

      //   clear cart
      this.cart.splice(0, length);
    },
  },
  computed: {
    calBill: function () {
      //   return this.cart.reduce((bill, item) => {
      //     return bill + item.total;
      //   }, 0);
      var bill = 0;
      for (var i = 0; i < this.cart.length; i++) {
        bill = bill + this.cart[i].total;
      }
      this.bill = bill;
      return bill;
    },
  },
};
</script>
<style>
.cat {
  color: rgb(15, 15, 15) !important;
  text-decoration: none;
}

.cat:hover {
  cursor: pointer;
  color: rgb(68, 6, 143) !important;
  text-decoration: underline;
}
</style>
