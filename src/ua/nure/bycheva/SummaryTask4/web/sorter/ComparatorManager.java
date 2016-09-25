package ua.nure.bycheva.SummaryTask4.web.sorter;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import ua.nure.bycheva.SummaryTask4.db.entity.Application;
import ua.nure.bycheva.SummaryTask4.db.entity.Faculty;
import ua.nure.bycheva.SummaryTask4.db.entity.Subject;

/**
 * Holder for all comparators. And
 *
 */
public class ComparatorManager {
	
	private static final Map<String, Comparator> comparators = new TreeMap<>();
	
	static{
		comparators.put("FNameAZ", new CompareFacultyByName());
		comparators.put("FNameZA", new CompareFacultyByNameReversed());
		comparators.put("FBudgPlace", new CompareFacultyByBudgetPlaces());
		comparators.put("FTotalPlace", new CompareFacultyByTotalPlaces());

		comparators.put("SNameAZ", new CompareSubjectByName());
		comparators.put("SNameZA", new CompareSubjectByNameReversed());

		comparators.put("Applications", new CompareApplications());
	}

    private static class CompareFacultyByNameReversed implements Comparator<Faculty>, Serializable {
        private static final long serialVersionUID = -5336823022419384451L;

        @Override
        public int compare(Faculty faculty1, Faculty faculty2) {
            return faculty2.getName().compareTo(faculty1.getName());
        }
    }

	private static class CompareFacultyByName implements Comparator<Faculty>, Serializable {
		private static final long serialVersionUID = -6740199604199050872L;

		@Override
		public int compare(Faculty faculty1, Faculty faculty2) {
			return faculty1.getName().compareTo(faculty2.getName());
		}
	}

	private static class CompareFacultyByBudgetPlaces implements Comparator<Faculty>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		@Override
		public int compare(Faculty faculty1, Faculty faculty2) {
			return Integer.compare(faculty1.getBudgetPlaces(), faculty2.getBudgetPlaces());
		}
	}

	private static class CompareFacultyByTotalPlaces implements Comparator<Faculty>, Serializable {
		private static final long serialVersionUID = -1573481565177573283L;

		@Override
		public int compare(Faculty faculty1, Faculty faculty2) {
			return Integer.compare(faculty1.getTotalPlaces(), faculty2.getTotalPlaces());
		}
	}

    private static class CompareSubjectByName implements Comparator<Subject>, Serializable {
        private static final long serialVersionUID = -5336823022419384451L;

        @Override
        public int compare(Subject subject1, Subject subject2) {
            return subject1.getName().compareTo(subject2.getName());
        }
    }

    private static class CompareSubjectByNameReversed implements Comparator<Subject>, Serializable {
        private static final long serialVersionUID = -5336823022419384451L;

        @Override
        public int compare(Subject subject1, Subject subject2) {
            return subject2.getName().compareTo(subject1.getName());
        }
    }

    private static class CompareApplications implements Comparator<Application>, Serializable {
        @Override
        public int compare(Application o1, Application o2) {
            return  Double.compare(o1.getAvgPoint(),o2.getAvgPoint());
        }
    }
    /**
     * Obtain comparator by the given parameter.
     * @param parameter string which contains short comparator name
     * @return Comparator object.
     */
	public static Comparator get(String parameter){
		return comparators.get(parameter);
	}



}