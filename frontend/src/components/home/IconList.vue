<template>
  <v-container fluid class="icons-container justify-center">
    <v-row class="d-flex justify-space-around px-16 icons-container">
      <div
        v-for="(p, index) in properties"
        :key="p.id"
        class="pa-2 d-flex flex-column align-center my-16"
      >
        <v-btn
          icon
          x-large
          class="align-self-center" id="property-icon"
          v-bind:style="{ color: colors[index % 5] }"
          ><v-icon x-large> {{ p.icon_name }} </v-icon>
        </v-btn>
        <div class="align-self-center cgreen--text">{{ p.name }}</div>
        <v-btn
          v-if="$store.state.admin"
          icon
          class="align-self-center"
          color="grey darken-3"
          @click.stop="openDialog(p)"
          ><v-icon small> fa-close </v-icon>
        </v-btn>
      </div>
      <v-dialog v-model="dialog" max-width="290">
        <v-card>
          <v-card-title> Biztos törölni szeretnéd? </v-card-title>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="cgreen darken-1" text @click="dialog = false">
              Mégse
            </v-btn>
            <v-btn color="corange darken-1" text @click="deleteProperty()">
              Törlés
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-row>
    <v-container v-if="$store.state.admin" class="d-flex justify-center pb-10">
      <v-card elevation="2" class="col-12 col-md-8 pb-10">
        <v-card-title class="text-h3 zabatana corange--text"
          >Tulajdonság hozzáadása</v-card-title
        >
        <v-card-text>
          <div class="d-flex pb-5">
            <p class="align-self-center ma-0">Ikonokat</p>
            <v-btn
              class="align-self-center"
              text
              small
              color="primary"
              href="https://fontawesome.com/v5.15/icons?d=gallery&p=2&q=swimming"
              target="blank"
            >
              innen
            </v-btn>
            <p class="align-self-center ma-0">
              lehet keresni. A kiválasztott ikon neve elé kell írni a "fa-"
              kifejezést.
            </p>
          </div>
          <v-row>
            <v-col cols="12" sm="6" md="6">
              <v-text-field
                v-model="newProperty.name"
                label="Tulajdonság"
                hint="pl.: Fürdési lehetőség"
                persistent-hint
                solo
              ></v-text-field>
            </v-col>
            <v-col cols="12" sm="6" md="5">
              <v-text-field
                v-model="newProperty.icon_name"
                label="Ikon"
                solo
                hint="pl.: fa-swimming-pool"
                persistent-hint
              ></v-text-field>
            </v-col>
            <v-col cols="6" sm="6" md="1">
              <v-icon large size="24px" color="cgreen">
                {{ newProperty.icon_name }}
              </v-icon>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-actions>
          <!-- <v-btn class="mx-2" fab dark color="clightgreen" @click="addIcon">
            <v-icon dark> fa-plus </v-icon>
          </v-btn> -->
          <v-btn
            elevation="2"
            color="cgreen"
            class="white--text"
            @click="addProperty"
            >MENTÉS
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
  </v-container>
</template>

<script>
export default {
  name: "CIconList",
  props: ["properties"],
  components: {},
  data: () => ({
    colors: ["darkblue", "orange", "purple", "green", "darkred"],
    newProperty: {
      name: "",
      icon_name: "",
    },
    dialog: false,
    propertyToBeRemoved: null
  }),
  methods: {
    deleteProperty() {
      this.dialog = false;
      this.$emit("delete-property", this.propertyToBeRemoved);
    },

    addProperty() {
      this.$emit("add-property", this.newProperty);
      this.newProperty = {
        name: "",
        icon_name: "",
      };
    },
    openDialog(p) {
      this.dialog = true;
      this.propertyToBeRemoved = p;
    },
  },
};
</script>
<style scoped>
.icons-container {
  background-color: #d4ecdd;
}
#property-icon{
  cursor: default;
}
</style>
