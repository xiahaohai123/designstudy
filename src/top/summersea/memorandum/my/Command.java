package top.summersea.memorandum.my;

import java.util.List;

public enum Command {
    LIST(":list") {
        @Override
        public void execute(Object... args) {
            List<String> arg = (List<String>) args[0];
            StringBuilder sb = new StringBuilder();
            for (String s : arg) {
                sb.append(s);
            }
            System.out.println(sb);
        }
    },
    UNDO(":undo") {
        @Override
        public void execute(Object... args) {
            List<String> arg = (List<String>) args[0];
            arg.remove(arg.size() - 1);
        }
    },
    EXIT(":exit") {
        @Override
        public void execute(Object... args) {
            System.exit(0);
        }
    },
    APPEND(":append") {
        @Override
        public void execute(Object... args) {
            List<String> arg = (List<String>) args[0];
            arg.add((String) args[1]);
        }
    };

    private String commandInput;

    Command(String commandInput) {
        this.commandInput = commandInput;
    }

    public void execute(Object... args) {
    }

    public static Command getCommand(String input) {
        if (UNDO.commandInput.equals(input)) {
            return UNDO;
        } else if (LIST.commandInput.equals(input)) {
            return LIST;
        } else if (EXIT.commandInput.equals(input)) {
            return EXIT;
        } else {
            return APPEND;
        }
    }
}
