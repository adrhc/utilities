# Copilot Instructions for utilities

## Build, Test, and Lint Commands

### Setup Environment
```bash
# Setup SDKMAN (macOS with Homebrew) - see DEV.md for details
source env.sh  # Automatically loads SDKMAN and Maven

# Verify Java and Maven versions
java -version
$MVN --version  # After env.sh sourced
```

### Build Commands
```bash
# Full build with tests (recommended)
./install.sh

# Full build skipping tests
./clean.install.skip-tests.sh

# Clean build with tests
./clean.install.sh

# Clean only (no build)
./clean.sh
```

### Run Tests
```bash
# Run all tests (unit and integration)
./mvnw test

# Run only unit tests
./mvnw surefire:test

# Run only integration tests
./mvnw failsafe:integration-test

# Run a specific test class
./mvnw test -Dtest=FilenameUtilsTest

# Run a specific test method
./mvnw test -Dtest=FilenameUtilsTest#sanitize

# Run tests with verbose output
./mvnw test -e
```

### Compilation
```bash
# Compile only (skip tests)
./mvnw clean compile
```

## High-Level Architecture

**utilities** is a Java utility library built with Spring Boot 3.5.14 and Maven. It provides reusable components across multiple domains:

- **Package Structure**: All classes are under `ro.go.adrhc.util.*` namespace
- **Core Modules**:
  - `value`: Value holders (`Counter`, `MutableValue`, `MutableBoolean`, `ValueHolder`, `IntHolder`)
  - `pair`: Pair data structures with specialized variants (`Pair`, `ComparablePair`, `UnaryPair`, `LongRightPair`, `HalfBooleanPair`)
  - `conversion`: Conversion utilities between types and Optional results
  - `io`: File and path utilities (`FileSystemUtils`, `PathUtils`, `FilenameUtils`, `FilesMetadataLoader`)
  - `collection`: Stream and collection utilities
  - `concurrency`: Streamer implementations for visiting structures and paths
- **Dependencies**: Minimal external deps (Spring Boot logging, Commons Lang3, Commons IO, Lombok for code generation)
- **Testing**: JUnit 5 with Awaitility for async operations

## Key Conventions

### Code Style
- **Lombok Usage**: All model/DTO classes use Lombok annotations to reduce boilerplate:
  - `@NoArgsConstructor` / `@AllArgsConstructor` for constructors
  - `@Getter` / `@Setter` for accessors
  - `@ToString` for string representation
  - When generating classes or adding fields, always consider Lombok annotations
- **Java Version**: Java 25 (with current compiler settings: `-parameters` flag enabled for reflection)
- **Encoding**: UTF-8 throughout

### Testing Conventions
- **Naming**: Test classes end with `Test` (e.g., `FilenameUtilsTest`)
- **Framework**: JUnit 5 with static imports from `org.junit.jupiter.api.Assertions`
- **Test Methods**: Use descriptive names reflecting what is being tested (e.g., `sanitize`, not `test1`)
- **Async Testing**: Use Awaitility for testing async code
- **Test Location**: Tests in `src/test/java` mirror production code structure under `src/main/java`

### Build Configuration
- **Maven Wrapper**: Use `./mvnw` for consistency (Maven 3.x wrapper provided)
- **Version Pattern**: `<java-version>-<spring-boot-version>` (e.g., `25-3.5.14`)
- **Compiler Config**: `-parameters` flag allows parameter name reflection; `-g` includes debug info
- **Test Plugins**: Surefire for unit tests, Failsafe for integration tests (both enabled by default)

### Package Organization
- Root package: `ro.go.adrhc.util`
- Subpackages by domain/functionality
- No application bootstrap class; this is a library artifact

## CI/CD
- Jenkins pipeline configured in `Jenkinsfile`
- Stages: Git pull, Build (uses `clean.install.sh`), Test result reporting
- Reports: Surefire and Failsafe reports collected to workspace
