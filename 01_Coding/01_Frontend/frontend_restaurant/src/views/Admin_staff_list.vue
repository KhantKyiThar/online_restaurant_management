<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <sidebar_admin></sidebar_admin>
      </v-col>

      <!-- staff Table -->
      <v-col cols="10">
        <v-row class="ma-0">
          <h1 class="mb-3">Staff List</h1>
          <v-spacer></v-spacer>
          <v-btn icon @click="onClickCreateStaff()">
            <v-icon size="30">mdi-plus-box</v-icon>
          </v-btn>
        </v-row>

        <v-data-table
          :headers="headers"
          :items="staffList"
          :items-per-page="5"
          class="elevation-1"
        >
          <!-- Button -->
          <template v-slot:[`item.actions`]="{ item }">
            <!-- Update Btn -->
            <v-btn
              class="mr-2"
              color="primary"
              fab
              x-small
              elevation="2"
              @click="onClickUpdateStaff(item)"
            >
              <v-icon>mdi-pencil</v-icon>
            </v-btn>

            <!-- Delete Btn -->
            <v-btn
              :disabled="!item.deleteable"
              color="error"
              fab
              x-small
              elevation="2"
              @click="
                toDeleteStaff = item;
                deleteDialog = true;
              "
            >
              <v-icon>mdi-delete</v-icon>
            </v-btn>
          </template>
        </v-data-table>
      </v-col>
    </v-row>

    <!-- Create Dialog -->
    <v-dialog v-model="createDialog" width="500">
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="pink lighten-1" dark>
          <div>Create Staff</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="createDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <!-- Create Info -->
        <v-card-text class="pa-4">
          <v-form ref="staffCreateForm" v-model="staffCreateForm">
            <!-- Create Staff Name -->
            <v-text-field
              v-model="name"
              :counter="10"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 10) ||
                  'Name must be less than 10 characters',
              ]"
              label="Name"
              required
            ></v-text-field>

            <!-- Create Staff Login ID -->
            <v-text-field
              v-model="loginId"
              :counter="10"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 10) ||
                  'Login ID must be less than 10 characters',
              ]"
              label="Login ID"
              required
            >
            </v-text-field>

            <!-- Password -->
            <v-text-field
              v-model="password"
              :counter="100"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 100) ||
                  'Password must be less than 100 characters',
              ]"
              label="Password"
              required
            ></v-text-field>

            <!-- Phone -->
            <v-text-field
              v-model="phone"
              :counter="30"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 30) ||
                  'Phone must be less than 30 characters',
              ]"
              label="Phone"
              required
            ></v-text-field>

            <!-- Gender -->
            <v-radio-group v-model="gender" row>
              <v-radio label="Male" value="Male"></v-radio>
              <v-radio label="Female" value="Female"></v-radio>
            </v-radio-group>

            <!-- Staff Type -->
            <v-select v-model="staffType" :items="items" label="Staff Type">
            </v-select>

            <!-- Joined Date -->
            <v-menu
              ref="menu"
              v-model="menu"
              :close-on-content-click="false"
              :return-value.sync="date"
              transition="scale-transition"
              offset-y
              min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="date"
                  label="Staff Joined Date"
                  prepend-icon="mdi-calendar"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="date" no-title scrollable>
                <v-spacer></v-spacer>
                <v-btn text color="primary" @click="menu = false">
                  Cancel
                </v-btn>
                <v-btn text color="primary" @click="$refs.menu.save(date)">
                  OK
                </v-btn>
              </v-date-picker>
            </v-menu>

            <!-- Create Btn -->
            <v-btn
              :disabled="!staffCreateForm"
              color="success"
              class="mr-4"
              @click="createStaff()"
            >
              <span v-if="!loading">Create</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
            </v-btn>
            <!-- Error Alert For Staff Create -->
            <v-alert class="mt-3" v-show="errorCreateAlert" dense type="error">
              {{ errMsg }}
            </v-alert>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>

    <!-- Delete staff Dialog -->
    <v-dialog v-model="deleteDialog" width="400">
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="pink lighten-1" dark>
          <div>Delete This Staff name?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="deleteDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>
        <!-- Delete Info -->
        <v-card-text class="pa-4">
          <v-row dense>
            <v-col cols="3" class="font-weight-bold">ID</v-col>
            <v-col cols="7">{{ toDeleteStaff.id }}</v-col>
            <v-col cols="3" class="font-weight-bold">Name</v-col>
            <v-col cols="7">{{ toDeleteStaff.name }}</v-col>
          </v-row>
        </v-card-text>
        <!-- Delete Btn -->
        <v-card-actions class="justify-end">
          <v-btn color="red" dark @click="deleteStaff()">Delete</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Update Staff Dialog -->
    <v-dialog v-model="updateDialog" width="500">
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="pink lighten-1" dark>
          <div>Update This Staff Name?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="updateDialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Update Info -->
        <v-card-text class="pa-4">
          <v-form ref="staffForm" v-model="staffForm">
            <!-- Update Staff Name -->
            <v-text-field
              v-model="toUpdateStaff.name"
              :counter="10"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 10) ||
                  'Name must be less than 10 characters',
              ]"
              label="Name"
              required
            ></v-text-field>

            <!-- Update Staff LoginId -->
            <v-text-field
              v-model="toUpdateStaff.loginId"
              :counter="10"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 10) ||
                  'Login ID must be less than 10 characters',
              ]"
              label="Login ID"
              required
            ></v-text-field>

            <!-- Update Staff Phone -->
            <v-text-field
              v-model="toUpdateStaff.phone"
              :counter="30"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 30) ||
                  'Phone must be less than 30 characters',
              ]"
              label="Phone"
              required
            ></v-text-field>

            <!-- Update Staff Gender -->
            <v-radio-group v-model="toUpdateStaff.gender" row>
              <v-radio label="Male" value="Male"></v-radio>
              <v-radio label="Female" value="Female"></v-radio>
            </v-radio-group>

            <!-- Update Staff StaffType -->
            <v-select
              v-model="toUpdateStaff.staffType"
              :items="items"
              label="Staff Type"
            >
            </v-select>

            <!-- Update Staff JoinedDate -->
            <v-text-field
              v-model="toUpdateStaff.joinedDate"
              :counter="30"
              :rules="[(v) => !!v || 'Required']"
              label="Joined Date"
              disabled
            ></v-text-field>

            <!-- Update Btn -->
            <v-card-actions class="justify-end"></v-card-actions>
            <v-btn
              :disabled="!staffForm"
              color="success"
              class="mr-4"
              @click="updateStaff"
            >
              <span v-if="!loading">Update</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
            </v-btn>

            <!-- Error Alert -->
            <v-alert class="mt-3" v-show="errorAlert" dense type="error">
              Update Staff Failed!
            </v-alert>
          </v-form>
        </v-card-text>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import utils from "../utils/utils";
import sidebar_admin from "../components/Sidebar_admin.vue";

export default {
  data: () => ({
    date: new Date(Date.now() - new Date(Date.now).getTimezoneOffset() * 60000)
      .toISOString()
      .substr(0, 10),
  }),
  name: "Admin_Staff_list",

  components: { sidebar_admin },

  data() {
    return {
      localDomain: utils.constant.localDomain,

      column: null,
      row: null,

      headers: [
        { text: "ID", value: "id", sortable: true },
        { text: "Name", value: "name", sortable: true },
        { text: "LoginId", value: "loginId", sortable: true },
        // { text: "Password", value: "password", sortable: true },
        { text: "Phone", value: "phone", sortable: true },
        { text: "Gender", value: "gender", sortable: true },
        { text: "Staff Type", value: "staffType", sortable: true },
        { text: "Joined Date", value: "joinedDate", sortable: true },
        // { text: "CreatedAt", value: "createdAt", sortable: true },
        // { text: "UpdatedAt", value: "updatedAt", sortable: false },
        { text: "Actions", value: "actions", sortable: false },
      ],
      staffList: [],

      deleteDialog: false,
      toDeleteStaff: {},

      createDialog: false,
      staffCreateForm: false,
      errorCreateAlert: false,
      name: "",
      loginId: "",
      password: "",
      phone: "",
      gender: "",
      staffType: "",
      // joinedDate: "",
      date: "",

      items: ["Admin", "Staff"],

      updateDialog: false,
      staffForm: false,
      toUpdateStaff: {
        name: "",
        loginId: "",
        password: "",
        phone: "",
        gender: "",
        staffType: "",
        joinedDate: "",
      },
      errorAlert: false,
      loading: false,
      errMsg: "",
      menu: false,
      secret: "123#$%",
      //   staffType: " ",
      //   staffTypeList: [],
    };
  },
  async created() {
    // await this.fetchStaffType();
    await this.fetchStaff();
  },

  methods: {
    async fetchStaff() {
      const resp = await utils.http.get("/admin/staff");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.staffList = data;
        }
      }
    },

    async deleteStaff() {
      const resp = await utils.http.del(
        "/admin/staff/delete/" + this.toDeleteStaff.id
      );
      if (resp && resp.status === 200) {
        this.deleteDialog = false;
        // Refresh staff List
        await this.fetchStaff();
      } else {
        this.errorAlert = true;
      }
    },

    onClickCreateStaff() {
      this.createDialog = true;
    },

    async createStaff() {
      // Form Validation
      if (this.$refs.staffCreateForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        const respStaff = await utils.http.post("/admin/staff/create", {
          name: this.name,
          loginId: this.loginId,
          password: this.password,
          gender: this.gender,
          phone: this.phone,
          staffType: this.staffType,
          // joinedDate: this.joinedDate,
          joinedDate: this.date,
        });
        if (respStaff && respStaff.status === 200) {
          //clear form inputs
          this.$refs.staffCreateForm.reset();
          this.createDialog = false;
          this.errorCreateAlert = false;
          this.errMsg = "";
          //refresh staff list
          await this.fetchStaff();
        } else {
          this.errorCreateAlert = true;
          const data = await respStaff.json();
          this.errMsg = data.message;
        }
        this.loading = false;
      }
    },

    onClickUpdateStaff(item) {
      this.updateDialog = true;
      //use Object.assign({}, item) instead of item
      //directly not to update in food list during fill the data in update form
      this.toUpdateStaff = Object.assign({}, item);
    },

    async updateStaff() {
      //Form validation
      if (this.$refs.staffForm.validate()) {
        this.errorAlert = false;
        this.loading = true;

        //Update staff
        const respStaff = await utils.http.put(
          "/admin/staff/update/" + this.toUpdateStaff.id,
          {
            name: this.toUpdateStaff.name,
            loginId: this.toUpdateStaff.loginId,
            // password: CryptoJS.AES.decrypt(
            //   this.toUpdateStaff.password,
            //   this.secret
            // ).toString(CryptoJS.enc.Utf8),
            gender: this.toUpdateStaff.gender,
            phone: this.toUpdateStaff.phone,
            staffType: this.toUpdateStaff.staffType,
            joinedDate: this.toUpdateStaff.joinedDate,
          }
        );
        if (respStaff && respStaff.status === 200) {
          this.updateDialog = false;
          //refresh staff list
          await this.fetchStaff();
        } else {
          this.errorAlert = true;
        }
        this.loading = false;
      }
    },
  },
};
</script>

<style>
</style>