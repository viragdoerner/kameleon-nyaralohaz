 <template>
  <v-row no-gutters class="d-flex flex-column flex-md-row col-12 pa-0">
    <v-col
      v-bind:class="{ 'order-md-2': index % 2 == 0 }"
      cols="6"
      style="min-width: 100px; max-width: 100%"
      class="flex-grow-1 flex-shrink-0 order-1 col-12 col-md-6"
    >
      <v-card
        v-bind:class="{ 'justify-md-end': index % 2 == 5 }"
        class="pa-2 justify-md-center align-md-center justify-start"
        color="white"
        height="100%"
      >
        <v-list
          class="px-md-16 px-4 py-10 overflow-x-auto"
          color="transparent"
          style="max-height: 100%, max-width: 100%"
        >
          <v-list-item v-for="item in apartment.properties" :key="item.title">
            <v-list-item-icon>
              <v-icon color="clightgreen"> fa-chevron-circle-right </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              {{ item.name }}
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card>
    </v-col>
    <v-col
      v-bind:class="{ 'order-md-1': index % 2 == 1 }"
      class="order-0 col-12 col-md-6"
    >
      <v-parallax dark :src="apartment.pictures[0]">
        <v-row align="center" justify="center">
          <v-col class="text-center" cols="12">
            <v-overlay absolute color="cgreen" class="d-flex flex-column">
              <v-row>
                <v-card-title class="text-h3 zabatana white--text">
                  {{ apartment.name }}</v-card-title
                >
              </v-row>
              <v-row>
                <v-btn
                  @click="toBooking()"
                  elevation="2"
                  x-large
                  color="corange"
                  class="ma-2"
                  >FOGLALÁS
                </v-btn>
                <v-btn
                  :to="getRoute"
                  elevation="2"
                  x-large
                  color="cyellow"
                  class="ma-2"
                  >BŐVEBBEN
                </v-btn>
              </v-row>
              <!-- <v-row class="mt-auto">
                <v-icon x-large color="white" id="chevron-icon" class="col-12">
                  mdi-chevron-double-down
                </v-icon>
              </v-row> -->
            </v-overlay>
          </v-col>
        </v-row>
      </v-parallax>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "CReserveApartmentSection",
  props: ["index", "apartment"],
  components: {},
  data: () => ({}),
  methods: {
    slugifyString: function (str) {
      str = str.replace(/^\s+|\s+$/g, ""); // trim
      str = str.toLowerCase();

      // remove accents, swap ñ for n, etc
      var from = "ãàáäâẽèéëêìíïîõòóöôùúüûñç·/_,:;";
      var to = "aaaaaeeeeeiiiiooooouuuunc------";
      for (var i = 0, l = from.length; i < l; i++) {
        str = str.replace(new RegExp(from.charAt(i), "g"), to.charAt(i));
      }

      str = str
        .replace(/[^a-z0-9 -]/g, "") // remove invalid chars
        .replace(/\s+/g, "-") // collapse whitespace and replace by -
        .replace(/-+/g, "-"); // collapse dashes

      return str;
    },
    toBooking() {
      this.$store.commit("selectApartmentId", this.apartment.id);
      this.$router.push("/booking");
    },
  },
  computed: {
    getRoute() {
      return "/" + this.slugifyString(this.apartment.name);
    },
  },
};
</script>
<style scoped>
</style>
