
import MomentService from '../services/moment.service';

class SortService {

    sortBookings(items, index, isDesc) {
        index = index[0];
        isDesc = isDesc[0];
        items.sort((a, b) => {
            if (index === "lastmodified") {
                return this.sortByLastModified(a, b, isDesc);
            } else if (index === "guestname") {
                return this.sortByGuestName(a, b, isDesc);
            } else {
                if (!isDesc) {
                    return a[index] < b[index] ? -1 : 1;
                } else {
                    return b[index] < a[index] ? -1 : 1;
                }
            }
        });
        return items;
    }
    sortByLastModified(a, b, isDesc) {
        var lastmodified_A = a.transitions.slice(-1)[0].created;
        var lastmodified_B = b.transitions.slice(-1)[0].created;
        var result = MomentService.sort(lastmodified_A, lastmodified_B, isDesc);
        return result;
    }
    sortByGuestName(a, b, isDesc) {
        var a_lastname = a.user.lastname;
        var b_lastname = b.user.lastname;
        if (isDesc) {
            if (a_lastname < b_lastname) return 1;
            if (a_lastname > b_lastname) return -1;
            return 0;
        } else {
            if (a_lastname < b_lastname) return -1;
            if (a_lastname > b_lastname) return 1;
            return 0;
        }
    }

}
export default new SortService();