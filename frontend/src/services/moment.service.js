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
    isGreatherThan(firstDate, secondDate) {
        return moment(firstDate) > moment(secondDate)
    }
}
export default new MomentService();