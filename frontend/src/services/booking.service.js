import moment from "moment";
import ApiService from "./api.service"
import store from "../store/index"

class BookingService {
    allowedDates(val, disabled_dates) {
        if (moment(val) < moment().add(5, "days")) {
            return false;
        }
        if (moment(val) > moment().add(2, "years")) {
            return false;
        }
        return !disabled_dates.includes(val);
    }
    getDaysBetweenDates(startDate, endDate) {
        startDate = moment(startDate);
        endDate = moment(endDate);
        var now = startDate.clone(),
            dates = [];

        while (now.isSameOrBefore(endDate)) {
            dates.push(now.format("YYYY-MM-DD"));
            now.add(1, "days");
        }
        return dates;
    }
    formatDate(d) {
        return (
            moment(d).locale("hu").format("LL") +
            " (" +
            moment(d).locale("hu").format("dddd") +
            ")"
        );
    }
    isGreatherThan(firstDate, secondDate) {
        return moment(firstDate) > moment(secondDate)
    }
    getTotalPriceForBooking(arrival, departure, price) {
        if (!!!arrival || !!!departure || !!!price) return 0;
        return (
            price *
            (this.getDaysBetweenDates(
                arrival,
                departure
            ).length -
                1)
        ).toLocaleString();
    }
    getDisabledDates(selectedApartment, dogIncluded) {
        return new Promise(function (resolve, reject) {
            if (!!selectedApartment) {
                ApiService.GET(
                    "booking/disabled_dates/" + selectedApartment.id + "/" + dogIncluded
                )
                    .then((response) => {
                        resolve(response.data);
                    })
                    .catch((error) => {
                        store.commit("showMessage", {
                            active: true,
                            color: "error",
                            message: "Hiba történt az elérhető dátumok lekérdezésénél",
                        });
                        reject(-1);
                    });
            }

        });
    }
    dateClick(dates, disabled_dates) {
        if (dates.length > 1) {
            if (this.isGreatherThan(dates[0], dates[1])) {
                [dates[0], dates[1]] = [dates[1], dates[0]];
            }
            if (dates[0] == dates[1]) {
                return [];
            } else {
                var dateList = this.getDaysBetweenDates(
                    dates[0],
                    dates[1]
                );
                const found = dateList.some(
                    (r) => disabled_dates.indexOf(r) >= 0
                );
                if (found) {
                    return [];
                }
                return dates;
            }
        }
        return dates;
    }
    bookingStatusAttrs(status, booking) {
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
                    info: "A foglalás véglegesítve lett, várunk szeretettel " + moment(booking.arrival).locale("hu").fromNow() + " (" +moment(booking.arrival).locale("hu").format("LL") + ") ! :)"
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
export default new BookingService();