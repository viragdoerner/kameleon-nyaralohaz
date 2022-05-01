<template>
  <v-card elevation="0">
    <v-card-text>
      <v-row>
        <v-col class="col-12 col-sm-6">
          <v-alert prominent text color="cgreen">
            <div class="mb-0">
              Kérjük ellenőrizd a foglalásod adatait, majd írj egy rövid
              üzenetet a házigazdának.
            </div>
            <div>
              Szükség esetén a lap alján található vissza gomb segítségével
              módosíthatod az előző oldalakon megadott adatokat.
            </div>
          </v-alert>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
          prepend-inner-icon="mdi-calendar-arrow-right"
            :value="booking.arrival"
            label="Érkezés napja"
            outlined
            color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field
          prepend-inner-icon="mdi-calendar-arrow-left"
            :value="booking.departure"
            label="Távozás napja"
            outlined
            color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field
           prepend-inner-icon="mdi-paw"
            :value="
              booking.dogIncluded ? 'Kutyussal jövünk' : 'Kutya nélkül jövünk'
            "
            outlined
            color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
      </v-row>

      <v-row>
        <v-col>
          <v-text-field
            :value="user.lastname"
            label="Vezetéknév"
            outlined
            color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field
            :value="user.firstname"
            label="Keresztnév"
            outlined
            color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-text-field
          prepend-inner-icon="mdi-phone"
            :value="user.phonenumber"
            label="Telefonszám"
            outlined
            color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
        <v-col>
          <v-text-field
          prepend-inner-icon="mdi-email"
            :value="user.email"
            label="E-mail-cím"
            outlined
            color="cgreen"
            dense
            readonly
          ></v-text-field>
        </v-col>
      </v-row>
      <v-divider class="my-5" ></v-divider>
      <v-row>
        <v-col class="col-12 col-sm-8">
          <v-textarea
            v-model="comment"
            label="Írj egy rövid üzenetet a házigazdának, hogy miért utazol és hány gyermek és felnőtt érkezik."
            solo
          ></v-textarea>
        </v-col>
      </v-row>
    </v-card-text>
    <v-card-actions>
      <v-btn color="corange" @click="finishStepThree" class="white--text" :disabled="comment.length<10">
        Foglalás elküldése
      </v-btn>
      <v-btn text @click="moveBack"> Vissza </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import ApiService from "../../services/api.service";

export default {
  name: "CBookingStepTwo",
  props: ["user", "booking"],
  components: {},
  data: () => ({
    comment: "",
  }),
  methods: {
    finishStepThree() {
      this.booking["comment"]= this.comment;
      ApiService.POST("booking", this.booking)
        .then((response) => {
          this.$emit("next-step", response.data);
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Nem sikerült lefoglalni az apartmant. Error üzenet: " + error,
          });
        });
      this.$emit("next-step");
    },
    moveBack() {
      this.$emit("back-step");
    },
  },
  computed: {},
  mounted() {
  },
};
</script>
