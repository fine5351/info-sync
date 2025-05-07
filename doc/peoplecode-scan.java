static class CompressedFunction {
    String n; // name
    List<String> a; // arguments
    String r; // return
    List<String> k; // keywords

    CompressedFunction(String name, List<String> args, String ret, List<String> keywords) {
        this.n = name;
        this.a = args;
        this.r = ret;
        this.k = keywords;
    }
}

static class CompressedFile {
    String f;
    List<CompressedFunction> fc = new ArrayList<>();

    CompressedFile(String f) {
        this.f = f;
    }
}

public static void main(String[] args) throws IOException {
    String sourceDir = "your/peoplecode/project/path";
    List<CompressedFile> snapshots = new ArrayList<>();

    Pattern funcPattern = Pattern.compile(
        "(Function|method)\\s+(\\w+)\\s*\\(([^\\)]*)\\)(\\s*Returns\\s+(\\w+))?",
        Pattern.CASE_INSENSITIVE
    );

    Set<String> outlineKeywords = Set.of("MessageBox", "SQLExec", "Select", "Evaluate", "If", "While", "For", "Component");

    Files.walk(Paths.get(sourceDir))
        .filter(p -> p.toString().endsWith(".pc") || p.toString().endsWith(".txt"))
        .forEach(path -> {
            try {
                List<String> lines = Files.readAllLines(path);
                CompressedFile cf = new CompressedFile(path.getFileName().toString());

                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i).trim();
                    Matcher matcher = funcPattern.matcher(line);

                    if (matcher.find()) {
                        String name = matcher.group(2).trim();
                        String paramRaw = matcher.group(3).trim();
                        String returnType = matcher.group(5) != null ? matcher.group(5).trim() : null;

                        List<String> paramList = new ArrayList<>();
                        if (!paramRaw.isEmpty()) {
                            String[] params = paramRaw.split(",");
                            for (String p : params) {
                                String[] parts = p.trim().split("\\s+");
                                if (parts.length >= 2) {
                                    paramList.add(parts[1] + ":" + parts[0]);
                                }
                            }
                        }

                        List<String> detected = new ArrayList<>();
                        int braceCount = 0;
                        for (int j = i + 1; j < lines.size(); j++) {
                            String inner = lines.get(j).trim();
                            if (inner.contains("{")) braceCount++;
                            if (inner.contains("}")) braceCount--;
                            for (String keyword : outlineKeywords) {
                                if (inner.contains(keyword) && !detected.contains(keyword)) {
                                    detected.add(keyword);
                                }
                            }
                            if (braceCount <= 0 && inner.contains("}")) break;
                        }

                        cf.fc.add(new CompressedFunction(name, paramList, returnType, detected));
                    }
                }

                if (!cf.fc.isEmpty()) snapshots.add(cf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    try (Writer writer = new FileWriter("snapshot_compressed.json")) {
        Gson gson = new Gson();
        gson.toJson(snapshots, writer);
    }

    System.out.println("✅ Compressed Snapshot 已輸出為 snapshot_compressed.json");
}
