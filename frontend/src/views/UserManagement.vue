<template>
  <v-container class="d-flex justify-center pa-0 pa-sm-3 pb-sm-10">
    <v-data-table
      :headers="headers"
      :items="users"
      class="elevation-1 col-12 "
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Felhasználók</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon small @click="confirmDeleteDialog(item)"> mdi-delete </v-icon>
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
    headers: [
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
    userToDelete: {
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
      ApiService.GET("user")
        .then((response) => {
          this.users = response.data;
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: "Hiba történt a felhasználók adatainak lekérésénél",
          });
        });
    },

    confirmDeleteDialog(item) {
      this.userToDelete = Object.assign({}, item);
      this.$store.commit("dialog/openSimpleDialog", {
        title: "Biztosan törölni szeretnéd?",
        confirmButton: "Törlés",
        onConfirm: () => {
          return this.deleteUser();
        },
      });
    },

    deleteUser() {
      ApiService.DELETE("user/" + this.userToDelete.id)
        .then((response) => {
          var that = this;
          var filteredUsers = this.users.filter(function (u) {
            return u.id !== that.userToDelete.id;
          });
          this.users = filteredUsers;

          this.$store.commit("showMessage", {
            active: true,
            color: "success", // You can create another actions for diferent color.
            message: "Sikeres törlés",
          });
        })
        .catch((error) => {
          var message = "Nem sikerült törölni a felhasználót";
          if (error.response) {
            message = message + ": " + error.response.data;
          }
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: message,
          });
        });
    },
  },
};
</script>


