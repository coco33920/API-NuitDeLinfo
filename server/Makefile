build:
	mvn clean install
fclean:
	mvn clean
clean:
	mkdir tmp/
	cp target/server-*.jar tmp/
	mvn clean
	mkdir target/
	cp tmp/* target/
	rm -r tmp/