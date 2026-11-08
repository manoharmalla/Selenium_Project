# Selenium Automation Knowledge Retrieval Workflow

## 1. Purpose of Knowledge Retrieval

This document defines how the GitHub Copilot Selenium automation testing agent gathers and validates project context before recommending or generating code. Retrieval should make answers consistent with the current repository, reduce unsupported assumptions, and provide only the context needed for the user's task.

This is a repository-search and file-reading workflow. It does not implement a retrieval-augmented generation service and does not imply that embeddings, vector search, or a vector database are configured.

## 2. Available Knowledge Sources

Use available sources in this order, according to the task:

- The user's current request, testcase, attachments, and explicit constraints.
- Actual source code under `src/main` and `src/test`, including page objects, shared setup, utilities, test classes, and tests.
- Build and test configuration such as `build.gradle`, Gradle wrapper configuration, and `src/test/resources/testng.xml`.
- Project documentation and applicable repository instructions.
- Relevant test results, diagnostics, reports, and failure artifacts when the task involves a failure or validation.

The project reference is `knowledge-base/selenium-project-knowledge.md`. Read it as a task-relevant starting point, then verify its claims against current source code. `.github/knowledge-base/project-guide.md` is additional supporting documentation and may be stale; do not prefer it over the source code or the more detailed project reference when they disagree.

The project reference describes a Java, Selenium WebDriver, TestNG, and Gradle project using page objects, shared browser setup in `BaseClass`, and `PageFactory`/`@FindBy` patterns. Treat this as a starting map, not proof that any particular class or method currently exists.

## 3. User Query Analysis

Before searching, identify:

- The requested outcome: add a test, debug a failure, explain code, or change framework behavior.
- The application flow and expected observable result, such as a product label, page title, or URL.
- Named files, classes, methods, locators, and relevant attachments.
- Constraints such as the required hybrid/POM pattern, use of `@FindBy`, or keeping browser launch in `BaseClass`.
- Whether the user expects implementation, investigation, or an explanation only.

Translate a natural-language testcase into ordered actions and assertions. Keep requirements separate from assumptions. Ask a concise clarification question only when an ambiguity blocks a correct implementation; otherwise state any material assumption and continue.

## 4. Relevant Knowledge Identification

Map each requirement to the smallest likely ownership boundary:

- Browser lifecycle and shared driver: inspect the current base class (documented as `base.BaseClass`).
- Page interaction and selectors: inspect the relevant page object under `src/main/java/pages/`.
- Reusable waits or actions: inspect the utility actually called by the page object.
- Scenario behavior and assertions: inspect related tests under `src/test/java/tests/`.
- Test execution and suite discovery: inspect `build.gradle` and, when applicable, `src/test/resources/testng.xml`.

Use documentation to locate likely files, then confirm the current implementation. Do not read unrelated modules simply because they are available.

## 5. Repository Search Strategy

Search narrowly and iteratively:

1. Search for the exact class, method, or locator named by the user.
2. Search for distinctive UI text or a relevant test method when the symbol is unknown.
3. Search filenames and paths for likely page objects, tests, and utilities.
4. Read the definition plus its immediate caller, test, or neighboring implementation needed to understand behavior.
5. Inspect build or suite configuration only when test discovery, dependencies, or execution scope matters.

Prefer repository-aware search and file-reading capabilities (for example, file search, text search, symbol references, and targeted reads). Use path filters to scope searches to `src/main`, `src/test`, and relevant documentation. Avoid broad searches through generated `build/`, `bin/`, reports, or dependencies unless generated output is the subject of the task.

Search terms should include exact UI labels, class names, method names, and relevant Selenium concepts. When the first search is inconclusive, refine it with a narrower path or alternate exact label instead of assuming the implementation.

## 6. Knowledge Retrieval Process

Follow this sequence for each task:

1. Understand the user's automation testing requirement.
2. Identify which project knowledge is relevant.
3. Search the repository for existing classes, methods, locators, and reusable components.
4. Read the relevant source files and knowledge documentation.
5. Retrieve only the information required for the current task.
6. Validate retrieved information against the actual source code.
7. Provide the relevant context to the AI agent before generating code.
8. If required knowledge is missing, ask for clarification or clearly report the limitation.

Do not skip source validation merely because documentation or a previous conversation describes an implementation.

## 7. Context Selection

Keep the context supplied to the coding agent small, task-focused, and actionable. Include only:

- The relevant user requirement and expected result.
- Verified source paths and the specific behavior they own.
- Existing helper, page-object, assertion, and test lifecycle patterns relevant to the change.
- Necessary build or suite configuration facts.
- Important constraints, known failures, and unresolved uncertainties.

Exclude unrelated test cases, credentials, personal data, and large file dumps. Prefer concise excerpts or a summary tied to the source file over copying whole modules.

## 8. Context Validation

Before using retrieved context:

- Confirm the file exists at the path being cited.
- Confirm classes, methods, fields, and locators in the source rather than relying on their names in documentation.
- Trace the relevant call path when behavior is delegated between a test, page object, utility, and base class.
- Check whether a test is discovered by Gradle, selected through TestNG XML, or both; do not assume the suite XML controls Gradle unless the build configuration says so.
- Compare documentation claims with current source and configuration.
- For a reported failure, inspect the actual error, test result, or artifact before assigning a cause.
- After code changes, run the narrowest meaningful validation available and report its result accurately.

If the source does not confirm a claim, label it as unverified or omit it.

## 9. Handling Missing Information

If a requested file, locator, method, test, or expected value is missing:

- Search likely source directories and relevant references once more with targeted terms.
- Check nearby implementations for an established pattern without assuming it applies unchanged.
- Ask the user when the missing detail affects correctness and cannot be inferred safely.
- Otherwise implement only what verified context supports and state the limitation.
- Do not silently substitute a URL, selector, product, or assertion for the requested UI behavior.

When a named knowledge source is absent, identify the absent path and any verified alternative source. Do not imply that the missing file was read.

## 10. Conflict Resolution

Resolve conflicting information using this precedence:

1. The user's latest explicit requirement or correction.
2. Current executable source and configuration.
3. Current, relevant tests and observed test results.
4. Repository instructions and documentation.
5. Prior conversation summaries or assumptions.

The actual source is authoritative for what currently exists. Documentation can describe intended conventions, but it may be outdated. Preserve user changes and unrelated edits. If two current sources disagree and the conflict affects the requested change, explain the conflict and ask for direction when necessary.

## 11. Knowledge Usage Rules

- Never assume a class or method exists without checking.
- Prefer actual source code over outdated documentation.
- Never invent project architecture.
- Do not retrieve unrelated project information.
- Follow existing framework conventions, including the repository's verified POM and hybrid patterns.
- Keep locators and user-facing page interactions in the page object when that matches the existing design.
- Keep shared browser lifecycle in its existing owner; do not duplicate launch behavior in a test or page object.
- Never expose secrets, credentials, tokens, or sensitive configuration values. Do not copy their values into prompts, generated documentation, logs, or reports.
- Never claim that vector search, embeddings, or a vector database are configured unless their implementation and configuration are found in the repository.
- Do not add retrieval dependencies or build a fake RAG subsystem for ordinary repository context retrieval.
- Clearly distinguish verified facts, assumptions, and limitations.

## 12. Retrieval Result Reporting

Before implementation, give a concise summary of the verified context when it helps orient the user: the relevant files, the existing pattern to follow, and any missing or conflicting information.

After implementation or investigation, report:

- What was changed or concluded, with links to the relevant repository files.
- Which focused validation was run and its actual outcome, or why validation could not run.
- Any unresolved locator, environment, source-document, or test-discovery limitation.
- Whether conclusions came from source code, documentation, or observed execution.

Do not report a test as passing unless its execution result confirms success. Do not describe a proposed or unverified behavior as implemented fact.
