<template>
  <v-container class="d-flex justify-center pa-0 pa-sm-3 pb-sm-10">
    <v-data-table
      :headers="headers"
      :items="users"
      class="elevation-1 col-12"
      :loading="loading"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Felhasználók</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
      <template v-slot:[`item.admin`]="{ item }">
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-icon v-if="item.admin" color="orange" v-bind="attrs" v-on="on">
              mdi-crown
            </v-icon>
            <v-icon v-else color="blue" v-bind="attrs" v-on="on">
              mdi-account
            </v-icon>
          </template>
          <span v-if="item.admin">Admin jogosultságú felhasználó</span>
          <span v-else>Átlagos felhasználó</span>
        </v-tooltip>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small @click="confirmDeleteDialog(item)"> mdi-delete </v-icon>
        <v-icon small @click="confirmRoleChange(item)"> mdi-crown </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize"> Reset </v-btn>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import ApiService from "../services/api.service";

export default {
  name: "CUserManagement",
  components: {},
  data: () => ({
    loading: false,
    headers: [
      {
        text: "",
        value: "admin",
      },
      {
        text: "Email",
        value: "email",
      },
      {
        text: "Vezetéknév",
        align: "start",
        value: "lastname",
      },
      {
        text: "Keresztnév",
        value: "firstname",
      },
      { text: "Telefonszám", sortable: false, value: "phonenumber" },
      { text: "", value: "actions", sortable: false },
    ],
    users: [],
    userToChange: {
      email: "",
      lastname: 0,
      firstname: 0,
      phonenumber: 0,
    },
  }),

  created() {
    this.initialize();
  },

  mounted() {},
  methods: {
    initialize() {
      this.loading = true;
      ApiService.GET("user")
        .then((response) => {
          this.users = response.data;
          this.loading = false;
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: "Hiba történt a felhasználók adatainak lekérésénél",
          });
          this.loading = false;
        });
    },
    initializeUserToChange() {
      this.userToChange = {
        email: "",
        lastname: 0,
        firstname: 0,
        phonenumber: 0,
      };
    },

    confirmDeleteDialog(user) {
      this.userToChange = Object.assign({}, user);
      this.$store.commit("dialog/openSimpleDialog", {
        title: "Biztosan törölni szeretnéd?",
        confirmButton: "Törlés",
        confirmButtonColor: "orange",
        onConfirm: () => {
          return this.deleteUser();
        },
      });
    },
    confirmRoleChange(user) {
      this.userToChange = Object.assign({}, user);
      this.$store.commit("dialog/openSimpleDialog", {
        title: "Módosítás megerősítése",
        text: user.admin
          ? "Biztosan törölni szeretnéd " +
            user.email +
            " felhasználó admin jogosultságait?"
          : "Biztosan admin jogosultságot szeretnél adni " +
            user.email +
            " felhasználónak?",
        confirmButton: "Igen",
        onConfirm: () => {
          return this.changeUserRole();
        },
      });
    },

    deleteUser() {
      this.loading = true;
      ApiService.DELETE("user/" + this.userToChange.id)
        .then((response) => {
          this.loading = false;
          var that = this;
          var filteredUsers = this.users.filter(function (u) {
            return u.id !== that.userToChange.id;
          });
          this.users = filteredUsers;
          this.initializeUserToChange();
          this.$store.commit("showMessage", {
            active: true,
            color: "success", // You can create another actions for diferent color.
            message: "Sikeres törlés",
          });
        })
        .catch((error) => {
          this.loading = false;
          var message = "Nem sikerült törölni a felhasználót";
          if (error.response) {
            message = message + ": " + error.response.data;
          }
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: message,
          });
          this.initializeUserToChange();
        });
    },
    changeUserRole() {
      this.loading = true;
      ApiService.PUT("user/changerole/" + this.userToChange.id)
        .then(() => {
          this.loading = false;
          var user = this.users.find((x) => x.id == this.userToChange.id);
          user.admin = !user.admin;
          this.$store.commit("showMessage", {
            active: true,
            color: "success",
            message: "Sikeres módosítás",
          });
          this.initializeUserToChange();
        })
        .catch((error) => {
          this.loading = false;
          var message = "Nem sikerült módosítani a felhasználót";
          if (error.response) {
            message = message + ": " + error.response.data;
          }
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: message,
          });
          this.initializeUserToChange();
        });
    },
  },
};
</script>


