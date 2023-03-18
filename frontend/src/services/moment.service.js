import moment from "moment";

class MomentService {
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
    formatDateTime(d) {
        return (
            moment(d).locale("hu").format("LLLL")
        );
    }
    formatDateFromNow(d) {
        return moment(d).locale("hu").fromNow();
    }
    isGreatherThan(firstDate, secondDate) {
        return moment(firstDate) > moment(secondDate)
    }
    sort(A, B, isDesc) {
        var dateA = moment(A);
        var dateB = moment(B);
        if (isDesc) {
            if (dateA.isBefore(dateB)) return 1;
            if (dateA.isAfter(dateB)) return -1;
            return 0;
        } else {
            if (dateA.isBefore(dateB)) return -1;
            if (dateA.isAfter(dateB)) return 1;
            return 0;
        }
    }
    removeDayOfDepartureFromBookingDates(departures) {
        var modifiedDates = [];
        departures.forEach(departure => {
            modifiedDates.push(moment(departure).subtract(1, 'days').format("YYYY-MM-DD"))
        })
        return modifiedDates
    }
}
export default new MomentService();