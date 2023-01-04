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
                  <v-list-item-title @click="showAllFood()"
                    >All</v-list-item-title
                  >
                  <v-list-item-title
                    v-for="(c, i) in category"
                    :key="i"
                    @click="showByCat(c)"
                    >{{ c.name }}</v-list-item-title
                  >
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
              <v-img :src="p.image" height="100" cover></v-img>
              <v-card-text>
                <div class="text-h5">{{ p.foodItem }}</div>
                <div class="text-body-1">Price : {{ p.price }} Kyat</div>
                <div class="text-body-1">Stock : {{ p.stock }} Unit</div>
                <div class="text-body-1 mb-1">
                  <v-btn @click="addToCard(p)" :disabled="p.stock <= 0">
                    Add To Cart
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
              <v-img :src="p.image" height="100" cover></v-img>
              <v-card-text>
                <div class="text-h5">{{ p.foodItem }}</div>
                <div class="text-body-1">Price : {{ p.price }} Kyat</div>
                <div class="text-body-1">Stock : {{ p.stock }} Unit</div>
                <div class="text-body-1 mb-1">
                  <v-btn @click="addToCard(p)" :disabled="p.stock <= 0">
                    Add To Cart
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
            <span @click="increase(item)" v-show="item.stock > 0"> + </span>
            {{ item.qty }}
            <span @click="reduce(item)" v-show="item.qty > 0"> - </span>
          </template>
          <template v-slot:[`item.total`]="{ item }">
            {{ item.total }}
            <span @click="remove(item)" class="ml-5">X</span>
          </template>
          <template slot="body.append">
            <tr v-show="this.cart.length > 0">
              <td><span @click="checkout()">Checkout</span></td>
              <td></td>
              <td>Total Price</td>
              <td>
                {{ calBill }}
                <span @click="cancel()" class="ml-5">X</span>
              </td>
            </tr>
          </template>
        </v-data-table>
      </v-col>
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
      product: [],
      category: [],
      cart: [],
      toShowAllFood: true,
      catId: 0,
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
      for (var i = 0; i < length; i++) {
        try {
          var isInProduct = this.product.find((v) => {
            return v.id == this.cart[i].id;
          });
          const resp = await utils.http.put(
            "/admin/food/update/" + isInProduct.id,
            {
              foodItem: isInProduct.foodItem,
              image: isInProduct.image,
              price: isInProduct.price,
              stock: isInProduct.stock,
              category: isInProduct.category,
            }
          );
        } catch (error) {
          console.log(error);
        }
      }
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
      return bill;
    },
  },
};
</script>
