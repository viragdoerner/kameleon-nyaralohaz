import moment from "moment";

class BookingDataService {

    bookingStatusAttrsForUser(status, booking) {
        switch (status) {
            case "TENTATIVE":
                return {
                    color: "cyellow",
                    icon: "mdi-clock",
                    title: "Foglalás alatt",
                    info: "Foglalásod sikeresen elmentettük, jelenleg feldoglozás alatt van. A nyaraló tulajdonosa 1-2 napon belül visszajelez, erről emailben is értesíteni fogunk!"
                };
            case "BOOKED":
                return {
                    color: "corange",
                    icon: "mdi-book",
                    title: "Lefoglalva, foglalóra vár",
                    info: "A nyaraló tulajdonosa elfogadta a foglalásod. A foglalás véglegesítéséhez egy héten belül foglalót kell fizetned. Ezt így meg így lehet megtenni. Kérdés esetén írj emailt, vagy hívj fel minket telefonon!"
                };
            case "PAID":
                return {
                    color: "clightgreen",
                    icon: "mdi-checkbox-marked-circle",
                    title: "Lefoglalva, foglaló kifizetve",
                    info: "A foglalás véglegesítve lett, várunk szeretettel " + moment(booking.arrival).locale("hu").fromNow() + " (" + moment(booking.arrival).locale("hu").format("LL") + ") ! :)"
                };
            case "DELETED":
                return {
                    color: "red",
                    icon: "mdi-close-circle",
                    title: "Törölve",
                    info: "A foglalásod törölve."
                };
            case "OUTDATED":
                return {
                    color: "purple",
                    icon: "mdi-timer-sand-empty",
                    title: "Lejárt",
                    info: "Ez egy régebbi, már nem aktuális foglalásod"
                };
            default:
                return {
                    color: "error",
                    icon: "mdi-emoticon-sad",
                    title: "Error ",
                    info: ""
                };
        }
    }
}
export default new BookingDataService();