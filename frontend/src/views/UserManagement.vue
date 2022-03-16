<template>
  <v-container class="d-flex justify-center pb-10">
    <v-data-table :headers="headers" :items="users" class="elevation-1 col-12">
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Felhasználók</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialogDelete" max-width="290">
            <v-card>
              <v-card-title> Biztos törölni szeretnéd? </v-card-title>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="cgreen darken-1" text @click="closeDelete">
                  Mégse
                </v-btn>
                <v-btn color="corange darken-1" text @click="deleteItemConfirm">
                  Törlés
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:item.actions="{ item }">
        <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize"> Reset </v-btn>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import ApiService from "../services/api.service"

export default {
  
  name: "CUserManagement",
  components: {},
  data: () => ({
    dialogDelete: false,
    headers: [
      { text: "Email", value: "email" },
      {
        text: "Vezetéknév",
        align: "start",
        value: "lastname",
      },
      { text: "Keresztnév", value: "firstname" },
      { text: "Telefonszám", sortable: false, value: "phonenumber" },
      { text: "", value: "actions", sortable: false },
    ],
    users: [],
    editedIndex: -1,
    editedItem: {
      email: "",
      lastname: 0,
      firstname: 0,
      phonenumber: 0,
    },
  }),

  watch: {
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },

  created() {
    this.initialize();
  },

  mounted() {},
  methods: {
    initialize() {
       ApiService.GET( "user")
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

    deleteItem(item) {
      this.editedIndex = this.users.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },

    deleteItemConfirm() {
      this.closeDelete();
       ApiService.DELETE( "user/" + this.editedItem.id)
        .then((response) => {
          this.users.splice(this.editedIndex, 1);
           this.$store.commit("showMessage", {
            active: true,
            color: "success", // You can create another actions for diferent color.
            message: "Sikeres törlés",
          });
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: "Nem sikerült törölni a felhasználót",
          });
        });
    },

    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    }
  },
};
</script>
