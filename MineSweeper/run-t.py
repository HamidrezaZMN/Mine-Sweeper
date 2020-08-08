#########################################################################
# This program runs the whole package together.                         #
# And it's good for GUI.                                                #
# Note: There should be only one main java file (like gui programs).    #
#########################################################################

# imports
import os

# check if ther is psv in the given file (or better to say, text) or not
def checker(text):
	psv = 'public static void main'
	for i in range(len(text)-len(psv)):
		if text[i:i+len(psv)]==psv:
			return True
	return False

# full path of the current py file
rpath = os.path.realpath(__file__)
rpath = rpath.split('\\')

# package of the java file
package = rpath[-2]

# extract java files
all_files = os.listdir()
java_files = []
for file in all_files:
	if file.endswith('.java'):
		java_files.append(file)
		
# extacting the java file with main method in it
main_file = ''
for java_file in java_files:
	file = open(java_file)
	if checker(file.read())==True:
		main_file = java_file
		file.close()
		break
	file.close()

# going up one directory
parent = os.path.dirname(os.getcwd())
os.chdir(parent)

# compile and running the package
os.system(f'javac -cp . {package}/{main_file}')
os.system(f'java -cp . {package}/{main_file}')

# going back to the package
os.chdir(package)

# deleting all the class files
for file in os.listdir():
	if file.endswith('.class'):
		os.system(f'del {file}')
		
