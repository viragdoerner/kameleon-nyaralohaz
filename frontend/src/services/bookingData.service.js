import moment from "moment";

class BookingDataService {

    bookingStatusAttrsForUser(status, booking) {
        switch (status) {
            case "TENTATIVE":
                return {
                    color: "cyellow",
                    icon: "mdi-clock",
                    status: "Foglalás alatt",
                    status_admin: "Új",
                    action_admin_icon_ok: "fa-calendar-check",
                    action_admin_ok: "Megerősítés",
                    action_admin_icon_cancel: "fa-ban",
                    action_admin_cancel: "Elutasítás",
                    info_admin: !!booking && booking.transitions.length>0 ? "Ez egy nemrég (" + moment(booking.transitions[booking.transitions.length - 1].created).locale("hu").fromNow() + ") létrehozott foglalás. Erősítsd meg " + booking.user.lastname + " " + booking.user.firstname + " foglalását, hogy utána befizethesse a foglalót! Ha esetleg nem megfelelő az időpont, utasítsd el." : "",
                    info: "Foglalásod sikeresen elmentettük, jelenleg feldoglozás alatt van. A nyaraló tulajdonosa 1-2 napon belül visszajelez, erről emailben is értesíteni fogunk!"
                };
            case "BOOKED":
                return {
                    color: "corange",
                    icon: "mdi-book",
                    status: "Lefoglalva, foglalóra vár",
                    status_admin: "Foglalóra vár",
                    action_admin_icon_ok: "fa-check-double",
                    action_admin_ok: "Foglaló kifizetve",
                    action_admin_icon_cancel: "fa-ban",
                    action_admin_cancel: "Elutasítás",
                    info_admin: !!booking ? "A foglalást visszaigazoltad, jelenleg a vendégre várunk, hogy befizesse a foglalót. Ha " + booking.user.lastname + " " + booking.user.firstname + " már elutalta a foglalót, erősítsd ezt meg! Ha több mint egy hete nem fizetett foglalót utasítsd el ezt a foglalást." : "",
                    info: "A nyaraló tulajdonosa elfogadta a foglalásod. A foglalás véglegesítéséhez egy héten belül foglalót kell fizetned. Ezt így meg így lehet megtenni. Kérdés esetén írj emailt, vagy hívj fel minket telefonon!"
                };
            case "PAID":
                return {
                    color: "clightgreen",
                    icon: "mdi-checkbox-marked-circle",
                    status: "Lefoglalva, foglaló kifizetve",
                    status_admin: "Foglaló kifizetve",
                    info_admin: "Ezzel a foglalással jelenleg nincs teendő. A foglalást már visszaigazoltad és a foglaló is befizetésre került.",
                    info: !!booking ? "A foglalás véglegesítve lett, várunk szeretettel " + moment(booking.arrival).locale("hu").fromNow() + " (" + moment(booking.arrival).locale("hu").format("LL") + ") ! :)" : ""
                };
            case "DELETED":
                return {
                    color: "red",
                    icon: "mdi-close-circle",
                    status: "Törölve",
                    status_admin: "Törölve",
                    info_admin: "Törölt vagy lemondott foglalás",
                    info: "A foglalásod törölve."
                };
            case "OUTDATED":
                return {
                    color: "purple",
                    icon: "mdi-timer-sand-empty",
                    status: "Lejárt",
                    status_admin: "Lejárt",
                    info_admin: "Ez egy régebbi, már nem aktuális foglalás.",
                    info: "Ez egy régebbi, már nem aktuális foglalásod"
                };
            default:
                return {
                    color: "error",
                    icon: "mdi-emoticon-sad",
                    status: "Error ",
                    status_admin: "Error",
                    info_admin: "",
                    info: ""
                };
        }
    }
}
export default new BookingDataService();