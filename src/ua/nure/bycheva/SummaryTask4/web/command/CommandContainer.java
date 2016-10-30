package ua.nure.bycheva.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all commands.
 *
 */
public class CommandContainer {

    private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<>();
	
	static{

        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());

        commands.put("noCommand", new NoCommand());

        commands.put("updateSettings", new UpdateSettingsCommand());
        commands.put("settingsView", new SettingsViewCommand());

        commands.put("listEntrants", new ListEntrantsCommand());
        commands.put("changeEntrantStatus", new ChangeEntrantStatusCommand());

        commands.put("listFaculties", new ListFacultiesCommand());
        commands.put("updateFaculty", new UpdateFacultyCommand());
        commands.put("editFaculty", new EditFacultyCommand());
        commands.put("deleteFaculty", new DeleteFacultyCommand());
        commands.put("addFaculty", new AddFacultyCommand());
        commands.put("saveFaculty", new SaveFacultyCommand());

        commands.put("applyFaculty", new ApplyFacultyCommand());
        commands.put("createApplication", new CreateApplicationCommand());
        commands.put("listApplications", new ListApplicationsCommand());
        commands.put("deleteApplication", new DeleteApplicationCommand());

        commands.put("addFacultySubject", new AddFacultySubjectCommand());
        commands.put("saveFacultySubject", new SaveFacultySubjectCommand());
        commands.put("deleteFacultySubject", new DeleteFacultySubjectCommand());


        commands.put("listMarks", new ListMarksCommand());
        commands.put("updateMarks", new UpdateEntrantMarkCommand());
        commands.put("deleteMark", new DeleteEntrantMarkCommand());
        commands.put("addMark", new AddEntrantMarkCommand());
        commands.put("saveMark", new SaveEntrantMarkCommand());


        commands.put("listSubjects", new ListSubjectsCommand());
        commands.put("deleteSubject", new DeleteSubjectCommand());
        commands.put("saveSubject", new SaveSubjectCommand());
        commands.put("editSubject", new EditSubjectCommand());
        commands.put("updateSubject", new UpdateSubjectCommand());

        commands.put("selectLanguage", new SelectLanguageCommand());

        commands.put("listSheets", new ListSheetsCommand());
        commands.put("addSheet", new AddSheetCommand());
        commands.put("saveSheet", new SaveSheetCommand());
        commands.put("deleteSheet", new DeleteSheetCommand());
        commands.put("showSheet", new ShowSheetCommand());
        commands.put("setSheetFinally", new SetSheetFinallyCommand());

        commands.put("entrantDetails", new EntrantDetailsCommand());
    }

    /**
     * Returns command object with the given name.
     *
     * @param cmdName
     *            Name of the command.
     * @return Command object.
     */
	public static Command get(String cmdName){
		if(cmdName == null || !commands.containsKey(cmdName)){
			return commands.get("noCommand");
		}
		
		return commands.get(cmdName);
	}

}
