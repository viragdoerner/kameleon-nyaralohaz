<template>
  <v-container fluid class="icons-container">
    <v-row
      class="
        d-flex
        justify-center justify-md-space-between
        px-md-16 px-5
        icons-container
        my-16
      "
    >
      <div
        v-for="(p, index) in properties"
        :key="p.id"
        class="
          pa-md-2
          px-4
          py-6
          d-flex
          align-center
          justify-start
          flex-column
           col-4 col-sm-2 col-md-1
        "
      >
        <v-btn
          icon
          x-large
          class=""
          id="property-icon"
          v-bind:style="{ color: colors[index % 5] }"
          ><v-icon x-large> {{ p.icon_name }} </v-icon>
        </v-btn>
        <div class="cgreen--text property-text">{{ p.name }}</div>
        <v-btn
          v-if="$store.getters.loggedIn && $store.getters.isAdmin"
          icon
          class=""
          color="grey darken-3"
          @click.stop="openDialog(p)"
          ><v-icon small> fa-close </v-icon>
        </v-btn>
      </div>
    </v-row>
    <v-container
      v-if="$store.getters.loggedIn && $store.getters.isAdmin"
      class="d-flex justify-center pb-10 pa-0 pa-md-2"
    >
      <v-card elevation="2" class="col-12 col-md-8  pb-10 pa-0 pa-md-2">
        <v-card-title class="text-h3 zabatana corange--text"
          >Tulajdonság hozzáadása</v-card-title
        >
        <v-card-text>
          <v-form ref="iconForm" v-model="valid" lazy-validation>
            <div class="d-flex flex-wrap pb-5 col-12">
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
                lehet keresni. 
              </p>
              <p class="align-self-center ma-0 col-12 col-sm-6 pa-0 pl-sm-1">
                A kiválasztott ikon nevét kell megadni.
              </p>
            </div>
            <v-row>
              <v-col cols="12" md="6">
                <v-text-field
                  v-model="newProperty.name"
                  label="Tulajdonság"
                  hint="pl.: Fürdési lehetőség"
                  persistent-hint
                  solo
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="8" md="4">
                <v-text-field
                  v-model="newProperty.icon_name"
                  label="Ikon"
                  solo
                  hint="pl.: fa-swimming-pool"
                  persistent-hint
                  :rules="[rules.required]"
                ></v-text-field>
              </v-col>
              <v-col cols="4" md="2">
                <v-icon large size="24px" color="cgreen">
                  {{ "fa-" + newProperty.icon_name }}
                </v-icon>
              </v-col>
            </v-row>
          </v-form>
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
  components: {  },
  data: () => ({
    colors: ["darkblue", "orange", "purple", "green", "darkred"],
    newProperty: {
      name: "",
      icon_name: "",
    },

    propertyToBeRemoved: null,
    rules: {
      required: (value) => !!value || "Kötelező.",
    },
    valid: true,
  }),
  methods: {
    deleteProperty() {
      this.$emit("delete-property", this.propertyToBeRemoved);
    },

    addProperty() {
      if (this.$refs.iconForm.validate()) {
        this.newProperty.icon_name = "fa-" + this.newProperty.icon_name;
        this.$emit("add-property", this.newProperty);
        this.newProperty = {
          name: "",
          icon_name: "",
        };
        this.$refs.iconForm.reset();
      }
    },
    openDialog(p) {
      this.$store.commit("dialog/openSimpleDialog", {
        title: "Biztosan törölni szeretnéd?",
        confirmButton: "Törlés",
        onConfirm: () => {
          return this.deleteProperty();
        }
      });
      this.propertyToBeRemoved = p;
    },
  },
};
</script>
<style scoped>
.icons-container {
  background-color: #d4ecdd;
}
#property-icon {
  cursor: default;
}
.property-text {
  text-align: center;
}
</style>
