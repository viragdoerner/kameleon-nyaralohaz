 <template>
  <v-container class="d-flex px-16 col-10" fluid>
    <v-card v-bind:class="{ 'order-2': index%2==0 }" width="50%" class="pa-10 ma-10"> bal - lista </v-card>
    <v-card v-bind:class="{ 'order-1': index%2==1 }" width="50%" class="pa-10 ma-10">
      jobb - kép és foglalás {{index}}
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "CReserveApartmentSection",
props: {
    index: {
      type: Number,
      required: true
    },
  },
  components: {},
};
</script>