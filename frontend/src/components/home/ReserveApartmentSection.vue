 <template>
  <v-row no-gutters class="d-flex align-stretch">
    <v-col
      v-bind:class="{ 'order-2': index % 2 == 0 }"
      cols="6"
      style="min-width: 100px; max-width: 100%"
      class="flex-grow-1 flex-shrink-0"
    >
      <v-card
        v-bind:class="{ 'justify-end': index % 2 == 5 }"
        class="pa-2 d-flex justify-center"
        color="cbggreen"
        height="100%"
      >
        <v-list class="px-16 py-10 align-stretch" color="transparent">
          <v-list-item v-for="item in apartment.properties" :key="item.title">
            <v-list-item-icon>
              <v-icon color="clightgreen"> fa-chevron-circle-right </v-icon>
            </v-list-item-icon>
            <v-list-item-content>
              <v-list-item-title v-text="item"></v-list-item-title>
            </v-list-item-content>
          </v-list-item>
        </v-list>
      </v-card>
    </v-col>
    <v-col v-bind:class="{ 'order-1': index % 2 == 1 }" cols="6">
      <v-parallax dark height="350" src="https://cdn.vuetifyjs.com/images/cards/house.jpg">
        <v-row align="center" justify="center">
          <v-col class="text-center" cols="12">
            <v-overlay absolute color="cgreen">
              <v-card-title class="text-h3 zabatana white--text">
                {{apartment.name}}</v-card-title
              >
              <v-btn elevation="2" x-large color="corange" class="ma-2"
                >FOGLALÁS
              </v-btn>
              <v-btn elevation="2" x-large color="cyellow" class="ma-2"
                >BŐVEBBEN
              </v-btn>
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
  data: () => ({
    items: [
      {
        icon: true,
        title: "Jason Oner",
        avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
      },
      {
        title: "Travis Howard",
        avatar: "https://cdn.vuetifyjs.com/images/lists/2.jpg",
      },
      {
        title: "Ali Connors",
        avatar: "https://cdn.vuetifyjs.com/images/lists/3.jpg",
      },
      {
        title: "Cindy Baker",
        avatar: "https://cdn.vuetifyjs.com/images/lists/4.jpg",
      },
    ],
  }),
};
</script>