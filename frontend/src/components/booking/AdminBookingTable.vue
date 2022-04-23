<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="bookings"
      :single-expand="singleExpand"
      :expanded.sync="expanded"
      item-key="id"
      show-expand
      class="elevation-1 col-12"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title>Felhasználók</v-toolbar-title>
          <v-divider class="mx-4" inset vertical></v-divider>
          <v-spacer></v-spacer>
          <confirm-dialog
            :confirmDialog="confirmDialog"
            v-on:confirm="cancelBooking"
          ></confirm-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.icon`]="{ item }">
        <v-icon :color="statusAttrs(item.status, item).color">
          {{ statusAttrs(item.status, item)["icon"] }}
        </v-icon>
      </template>
      <template v-slot:[`item.status`]="{ item }">
        <div class="overline">
          {{ statusAttrs(item.status, item).status_admin }}
        </div>
      </template>
      <template v-slot:[`item.user.lastname`]="{ item }">
        {{ item.user.lastname + " " + item.user.firstname }}
      </template>
      <template v-slot:[`item.lastmodified`]="{ item }">
        {{
          item.transitions[item.transitions.length - 1] ? formatDateTime(item.transitions[item.transitions.length - 1].created) : ""
        }}
      </template>
      <template v-slot:[`item.arrival`]="{ item }">
        {{ formatDate(item.arrival) }}
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              v-if="item.status == 'TENTATIVE' || item.status == 'BOOKED'"
              fab
              v-bind="attrs"
              v-on="on"
              @click="openOKDialog(item)"
              x-small
              color="transparent"
              class="mt-auto align-self-end mr-2"
              ><v-icon small color="success">
                {{ statusAttrs(item.status, item).action_admin_icon_ok }}
              </v-icon>
            </v-btn>
          </template>
          <span class="text-caption">{{
            statusAttrs(item.status, item).action_admin_ok
          }}</span>
        </v-tooltip>
        <v-tooltip bottom>
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              v-if="item.status == 'TENTATIVE' || item.status == 'BOOKED'"
              fab
              v-bind="attrs"
              v-on="on"
              @click="openCancelDialog(item)"
              x-small
              color="transparent"
              class="mt-auto align-self-end mr-2"
              ><v-icon small color="corange">
                {{ statusAttrs(item.status, item).action_admin_icon_cancel }}
              </v-icon>
            </v-btn>
          </template>
          <span class="text-caption">{{
            statusAttrs(item.status, item).action_admin_cancel
          }}</span>
        </v-tooltip>
      </template>
      <template v-slot:no-data>
        <v-btn color="primary" @click="initialize"> Frissítés </v-btn>
      </template>
      <template v-slot:expanded-item="{ headers, item }">
        <td :colspan="headers.length">
          <booking-tabs
            :booking="item"
            :admin="true"
            class="py-4"
          ></booking-tabs>
        </td>
      </template>
    </v-data-table>
    <confirm-dialog
      :confirmDialog="confirmDialog"
      v-on:confirm="cancelBooking"
    ></confirm-dialog>
  </div>
</template>

<script>
import ApiService from "../../services/api.service";
import BookingDataService from "../../services/bookingData.service";
import BookingTabs from "./BookingTabs.vue";
import ConfirmDialog from "../ConfirmDialog.vue";
import MomentService from "../../services/moment.service";

export default {
  name: "CUserBookingExpPanels",
  components: { BookingTabs, ConfirmDialog },
  props: ["bookings", "active"],
  data: () => ({
    expanded: [],
    singleExpand: false,
    headers: [
      { text: "", value: "data-table-expand" },
      {
        text: "",
        align: "end",
        value: "icon",
        sortable: false,
      },
      {
        text: "Státusz",
        align: "start",
        value: "status",
      },
      { text: "Legutoljára módosítva", value: "lastmodified" },
      { text: "Vendég", value: "user.lastname" },
      { text: "Érkezés", value: "arrival" },
      { text: "", value: "actions", sortable: false },
    ],
    confirmDialog: {
      isOpen: false,
      title: "Foglalás lemondása",
      text: "Biztosan le szeretnéd mondani a foglalást? Amennyiben már kifizetted a foglalót az nem jár vissza.",
      confirmButton: "OK",
      commentForm: {
        textfieldLabel:
          "Kérjük indokold meg, hogy mi miatt mondod le a foglalást!",
      },
    },
    bookingToBeRemoved: {},
  }),
  mounted() {},
  computed: {},
  methods: {
    formatDate(d) {
      return MomentService.formatDate(d);
    },
    formatDateTime(d) {
      return MomentService.formatDateTime(d);
    },
    statusAttrs(status, booking) {
      return BookingDataService.bookingStatusAttrsForUser(status, booking);
    },
    openDialog(item) {
      this.confirmDialog.isOpen = true;
      this.bookingToBeRemoved = item;
    },
    cancelBooking(comment) {
      console.log(comment);
      var payload = {
        comment: comment,
      };
      console.log(payload);
      ApiService.PUT("booking/cancel/" + this.bookingToBeRemoved.id, payload)
        .then((response) => {
          console.log(response.data);
          if (this.active != response.data.active) {
            console.log("at kell tenni a masik csoportba");
            this.$emit("activeStateChanged", response.data);
          }
          this.$store.commit("showMessage", {
            active: true,
            color: "success", // You can create another actions for diferent color.
            message: "Foglalás sikeresen lemondva",
          });
        })
        .catch((error) => {
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message:
              "Nem sikerült lemondani a foglalást. Próbáld újra, vagy mondd le telefonon vagy emailben!",
          });
        });
    },
  },
};
</script>
