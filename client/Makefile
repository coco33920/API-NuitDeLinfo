PM = npm
JS_DEPS = node_modules
BUILD = dist

# Colors
ifneq ($(shell tput colors),0)
    C_BOLD=\e[1m
    C_RESET=\033[00m
    C_BLUE=\e[34m
    C_RED=\e[31m
    C_CYAN=\e[36m
else
    C_BOLD=
    C_RESET=
    C_BLUE=
    C_CYAN=
endif

# Definitions
_ECHO  = /bin/echo -e

OK = @ $(_ECHO) "$(C_BOLD)[$(C_BLUE)OK$(C_RESET)$(C_BOLD)]$(C_RESET)"
KO = @ $(_ECHO) "$(C_BOLD)[$(C_RED)KO$(C_RESET)$(C_BOLD)]$(C_RESET)"
NTD = @ $(_ECHO) "$(C_BOLD)Nothing to do.$(C_RESET)"

# Errors
DIE = exit 1

all: $(BUILD)
	$(NTD)

%:
	$(KO) "Unknown directive $@" && $(DIE)

$(JS_DEPS):
	@ $(PM) install
	$(OK) "Dependencies installed"

$(BUILD): $(JS_DEPS)
	@ $(PM) run build
	$(OK) "Created production build"

run: $(BUILD)
	$(PM) run dev

lint: $(JS_DEPS)
	$(PM) run lint
	$(NTD)

.PHONY: run lint

clean:
	@ rm -rf $(BUILD)
	$(OK) "Remove $(C_CYAN)build$(RESET)"

fclean: clean
	@ rm -rf $(JS_DEPS)
	$(OK) "Remove $(C_CYAN)deps$(RESET)"

re: fclean all

.PHONY: clean fclean re
