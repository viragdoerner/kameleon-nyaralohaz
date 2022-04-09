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
        if (!!!arrival || !!!departure) return 0;
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
}
export default new BookingService();