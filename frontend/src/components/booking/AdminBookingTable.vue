<template>
  <div>
    <v-data-table
      :headers="headers"
      :items="bookings"
      :single-expand="singleExpand"
      :expanded.sync="expanded"
      item-key="id"
      show-expand
      :custom-sort="customSort"
      class="elevation-1 col-12"
    >
      <template v-slot:top>
        <v-toolbar flat>
          <v-toolbar-title v-if="active">Aktív foglalások</v-toolbar-title>
          <v-toolbar-title v-else>Inaktív foglalások</v-toolbar-title>

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
      <template v-slot:[`item.guestname`]="{ item }">
        {{ item.user.lastname + " " + item.user.firstname }}
      </template>
      <template v-slot:[`item.lastmodified`]="{ item }">
        {{
          item.transitions[item.transitions.length - 1]
            ? formatDateTime(
                item.transitions[item.transitions.length - 1].created
              )
            : ""
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
              @click="mouseClickAccept(item)"
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
              @click="mouseClickCancel(item)"
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
          <div class="d-flex">
            <booking-tabs
              :booking="item"
              :admin="true"
              class="py-4 col-10"
            ></booking-tabs>
            <div class="col-2">
              <v-btn
                v-if="item.status == 'TENTATIVE' || item.status == 'BOOKED'"
                large
                text
                class="d-flex justify-start caption"
                @click="mouseClickAccept(item)"
              >
                <v-icon small left color="success">
                  {{ statusAttrs(item.status, item).action_admin_icon_ok }}
                </v-icon>
                {{ statusAttrs(item.status, item).action_admin_ok }}
              </v-btn>
              <v-btn
                v-if="item.status == 'TENTATIVE' || item.status == 'BOOKED'"
                large
                text
                class="d-flex justify-start caption"
                @click="mouseClickCancel(item)"
              >
                <v-icon small left color="corange">
                  {{ statusAttrs(item.status, item).action_admin_icon_cancel }}
                </v-icon>
                {{ statusAttrs(item.status, item).action_admin_cancel }}
              </v-btn>
              <v-btn
                large
                text
                class="d-flex justify-start caption"
                @click="mouseClickUpdate(item)"
              >
                <v-icon small left color="primary"> fa-pencil </v-icon>
                Állapot módosítás
              </v-btn>
              <v-btn
                large
                text
                class="d-flex justify-start caption"
                @click="mouseClickDelete(item)"
              >
                <v-icon small left color="error"> fa-trash </v-icon>
                Végleges törlés
              </v-btn>
            </div>
          </div>
        </td>
      </template>
    </v-data-table>
    <confirm-dialog v-on:confirm="dialogOkEvent"></confirm-dialog>
  </div>
</template>

<script>
import ApiService from "../../services/api.service";
import BookingDataService from "../../services/bookingData.service";
import BookingTabs from "./BookingTabs.vue";
import ConfirmDialog from "../ConfirmDialog.vue";
import MomentService from "../../services/moment.service";
import SortService from "../../services/sort.service";

export default {
  name: "CAdminBookingTable",
  components: { BookingTabs, ConfirmDialog },
  props: ["bookings", "active"],
  data: () => ({
    expanded: [],
    singleExpand: true,
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
      { text: "Vendég", value: "guestname" },
      { text: "Érkezés", value: "arrival" },
      { text: "", value: "actions", sortable: false },
    ],
    selectedBooking: {},
    payload: {
      comment: "",
      newStatus: "",
    },
    actionType: "",
  }),
  mounted() {},
  computed: {},
  methods: {
    customSort(items, index, isDesc) {
      return SortService.sortBookings(items, index, isDesc);
    },
    mouseClickAccept(item) {
      this.selectedBooking = item;
      this.actionType = "accept";
      if (item.status == "TENTATIVE") {
        this.payload.newStatus = "BOOKED";
      }
      if (item.status == "BOOKED") {
        this.payload.newStatus = "PAID";
      }

      this.$store.commit("dialog/openDialogWithForm", {
        title: this.statusAttrs(item.status, item).action_admin_ok,
        confirmButton: "OK",
        form: {
          textfieldLabel:
            "Írj rövid üzenetet, egyéb megjegyzést a vendégnek, ha szeretnél.",
          textfieldRequired: false,
        },
      });
      return;
    },
    mouseClickCancel(item) {
      this.selectedBooking = item;
      this.actionType = "cancel";
      if (item.status == "TENTATIVE" || item.status == "BOOKED") {
        this.payload.newStatus = "DELETED";
      }
      this.$store.commit("dialog/openDialogWithForm", {
        title: this.statusAttrs(item.status, item).action_admin_cancel,
        text: "Biztosan le szeretnéd mondani a foglalást? Amennyiben már kifizetted a foglalót az nem jár vissza.",
        confirmButton: "ELUTASÍTÁS",
        confirmButtonColor: "error",
        form: {
          textfieldLabel: "Írj indoklást a vendégnek!",
          textfieldRequired: true,
        },
      });
      return;
    },
    mouseClickUpdate(item) {
      this.selectedBooking = item;
      this.actionType = "update";
      this.$store.commit("dialog/openDialogWithForm", {
        title: "Foglalás státuszának módosítása",
        text: "Ez a funkció csak különleges esetek kezelésére szolgál. Csak akkor használd, ha megfelelő indokod van rá!",
        confirmButton: "MÓDOSÍTÁS",
        confirmButtonColor: "success",
        form: {
          textfieldLabel:
            "Írj magyarázatot a vendéhnek! Mi miatt módosul a foglalás státusza?",
          textfieldRequired: false,
          dropdownLabel: "Válaszd ki a foglalás új állapotát",
          dropdownItems: [
            {
              name: this.statusAttrs("TENTATIVE", item).status_admin,
              status: "TENTATIVE",
            },
            {
              name: this.statusAttrs("BOOKED", item).status_admin,
              status: "BOOKED",
            },
            {
              name: this.statusAttrs("PAID", item).status_admin,
              status: "PAID",
            },
            {
              name: this.statusAttrs("DELETED", item).status_admin,
              status: "DELETED",
            },
            {
              name: this.statusAttrs("OUTDATED", item).status_admin,
              status: "OUTDATED",
            },
          ].filter(function (s) {
            return s.status !== item.status;
          }),
        },
      });
      return;
    },
    mouseClickDelete(item) {
      this.actionType = "delete";
      this.selectedBooking = item;
      this.$store.commit("dialog/openDialog", {
        title: "Foglalás végleges törlése",
        text: "Biztosan ki szeretnéd véglegesen törölni a foglalást? Ezt a műveletet nem lehet visszavonni.",
        confirmButton: "TÖRLÉS",
        confirmButtonColor: "red",
      });
      return;
    },
    dialogOkEvent(result) {
      if (this.actionType === "delete") {
        this.deleteBooking();
      } else {
        if (this.actionType === "update") {
          this.payload.newStatus = result.newStatus;
        }
        this.payload.comment = result.comment;
        ApiService.PUT("booking/" + this.selectedBooking.id, this.payload)
          .then((response) => {
            this.$emit("statusChanged", response.data);
            this.$store.commit("showMessage", {
              active: true,
              color: "success", // You can create another actions for diferent color.
              message: "Foglalás státusza sikeresen módosítva",
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
      }
      return;
    },
    deleteBooking() {
      ApiService.DELETE("booking/" + this.selectedBooking.id)
        .then((response) => {
          var that = this;
          this.bookings = this.bookings.filter(function (booking) {
            return booking.id !== that.selectedBooking.id;
          });
          this.$store.commit("showMessage", {
            active: true,
            color: "success", // You can create another actions for diferent color.
            message: "Foglalás sikeresen törölve",
          });
        })
        .catch((error) => {
          console.log(error);
          this.$store.commit("showMessage", {
            active: true,
            color: "error",
            message: "Nem sikerült törölni a foglalást",
          });
        });
    },
    formatDate(d) {
      return MomentService.formatDate(d);
    },
    formatDateTime(d) {
      return MomentService.formatDateTime(d);
    },
    statusAttrs(status, booking) {
      return BookingDataService.bookingStatusAttrsForUser(status, booking);
    },
  },
};
</script>
