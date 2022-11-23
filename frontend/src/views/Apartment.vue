<template>
  <div v-if="mutableApartment.pictures">
    <c-description-section
      :data="mutableApartment"
      v-on:update-apartment="saveApartment"
    ></c-description-section>
    <c-gallery-section
      :apartment="mutableApartment"
      @updateApartment="onUpdateApartment"
    ></c-gallery-section>
    <c-icon-list
      :properties="mutableApartment.properties"
      v-on:add-property="onAddProperty"
      v-on:delete-property="onDeleteProperty"
    ></c-icon-list>
  </div>
</template>

<script>
import CHeader from "../components/home/Header.vue";
import CDescriptionSection from "../components/apartment/DescriptionSection.vue";
import CGallerySection from "../components/apartment/GallerySection.vue";
import CIconList from "../components/home/IconList.vue";
import ApiService from "../services/api.service";

export default {
  name: "CApartment",
  props: ["apartment"],
  components: {
    CHeader,
    CDescriptionSection,
    CGallerySection,
    CIconList,
  },
  data: () => ({
    mutableApartment: {},
  }),
  mounted() {
    this.mutableApartment =  JSON.parse(JSON.stringify(this.apartment));
  },
  methods: {
    onUpdateApartment(updatedApartment) {
      this.mutableApartment = updatedApartment;
    },
    onAddProperty(p) {
      var payload = JSON.parse(JSON.stringify(this.mutableApartment));
      payload.properties.push(p);
      this.saveApartment(payload);
    },
    onDeleteProperty(p) {
      var payload = JSON.parse(JSON.stringify(this.mutableApartment));
      payload.properties = payload.properties.filter(function (property) {
        console.log(property);
        console.log(p);
        return property.id !== p.id;
      });
      this.saveApartment(payload);
    },
    saveApartment(payload) {
      ApiService.PUT("apartment", payload)
        .then((response) => {
          this.mutableApartment = response.data;

          this.$store.commit("showMessage", {
            active: true,
            color: "cgreen", // You can create another actions for diferent color.
            message: "Sikeres mentés",
          });
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error", // You can create another actions for diferent color.
            message: "Sikertelen mentés",
          });
        });
    },
    created() {},
    mounted() {},
  },
};
</script>
