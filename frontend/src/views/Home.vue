<template>
  <div>
    <c-header></c-header>
    <c-reserve-section></c-reserve-section>
    <c-icon-list></c-icon-list>
    <c-reserve-apartment-section
      v-for="(apartment, index) in apartments"
      v-bind:key="apartment.id"
      v-bind:index="index"
    ></c-reserve-apartment-section>
  </div>
</template>

<script>
import CHeader from "./Header.vue";
import CReserveSection from "./ReserveSection.vue";
import CReserveApartmentSection from "./ReserveApartmentSection.vue";
import CIconList from "./IconList.vue";

export default {
  name: "Home",

  components: {
    CHeader,
    CReserveSection,
    CIconList,
    CReserveApartmentSection
  },
  data: () => ({
    apartments: [
      { name: "Földszinti apartman", id: 1 },
      { name: "Emeleti apartman", id: 2 },
      { name: "Harmadik apartman", id: 3 },
    ],
  }),
};
</script>
