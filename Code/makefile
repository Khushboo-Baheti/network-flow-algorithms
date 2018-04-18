# Java compiler
JAVAC = javac
# Update this JVM version to be the one installed on machine. Check using java --version or more verbose command java -XshowSettings:properties -version
JVM = 9

# Directory for compiled binaries
# - trailing slash is important!
BIN = ./bin/

# Directory of source files
# - trailing slash is important!
SRC = ./src/

# Java compiler flags
JAVAFLAGS = -Xlint:unchecked -Xlint:deprecation -g -d $(BIN) -cp $(SRC) -target $(JVM)

# Creating a .class file
COMPILE = $(JAVAC) $(JAVAFLAGS)

EMPTY = 

JAVA_FILES = $(subst $(SRC), $(EMPTY), $(wildcard $(SRC)*.java))

ALL_FILES = $(JAVA_FILES)

# One of these should be the "main" class listed in Runfile
CLASS_FILES = $(ALL_FILES:.java=.class)

# The first target is the one that is executed when you invoke
# "make". 

all: $(addprefix $(BIN), $(CLASS_FILES))

# The line describing the action starts with <TAB>
$(BIN)%.class: $(SRC)%.java
	$(COMPILE) $<

clean : 
	rm -rf $(BIN)
