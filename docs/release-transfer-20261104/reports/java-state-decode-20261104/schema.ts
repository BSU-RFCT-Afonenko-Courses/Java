import { z } from "file:///home/tolya/JavaCodex/java-release-20261110/java/tasks/_extensions/Afonenko-Course-Tools/course-core/vendor/zod/v4/index.js";
import { SupportedQuartoVersionSchema } from "file:///home/tolya/JavaCodex/java-release-20261110/java/tasks/_extensions/Afonenko-Course-Tools/course-core/infrastructure/quarto-version.ts";
const pathSchema = z.string().min(1).refine((s) =>
  !s.startsWith("/") && !s.includes("\\") && !s.includes("\0") &&
  s.split("/").every((p) => p && p !== "." && p !== "..")
);
const rowSchema = z.strictObject({
  path: pathSchema,
  signature: z.string(),
  sha256: z.string(),
});
const stateSchema = z.strictObject({
  schema: z.literal("course-native-operation-private-v2"),
  operation: z.uuid(),
  root: z.string(),
  directory: z.string(),
  outputDirectory: z.string(),
  profiles: z.array(z.string()),
  projection: z.enum(["student", "author"]),
  captureWitnesses: z.record(pathSchema, z.uuid()),
  assignment: z.strictObject({
    assessments: z.array(
      z.strictObject({ source: pathSchema, id: z.string().min(1) }),
    ).min(1).max(4096),
    runtime: z.strictObject({
      executable: z.string().startsWith("/"),
      environment: z.record(z.string(), z.string()),
      profiles: z.array(z.string()),
      outputDirectory: z.string(),
      inputs: z.array(pathSchema),
      implementationFiles: z.array(
        z.strictObject({ path: z.string(), signature: z.string() }),
      ),
    }),
  }).optional(),
  invocation: z.string(),
  localInvocation: z.string(),
  inputs: z.array(pathSchema),
  sourcePolicy: z.enum(["general", "static"]).default("general"),
  rows: z.array(rowSchema),
  relevantInputs: z.array(pathSchema),
  ownerInputs: z.array(pathSchema).default([]),
  ownerWitness: z.strictObject({
    quarto: z.strictObject({ version: SupportedQuartoVersionSchema }),
    dir: z.string(),
    config: z.record(z.string(), z.unknown()),
    files: z.strictObject({
      input: z.array(z.string()),
      config: z.array(z.string()),
    }),
  }).optional(),
  configurationInputs: z.array(pathSchema),
  documentInputs: z.record(pathSchema, z.array(pathSchema)),
  memberships: z.record(z.string(), z.array(z.string())),
  metrics: z.strictObject({
    inspectCalls: z.number(),
    fullSourceFences: z.number(),
    localSourceFences: z.number(),
    sourceDirectoryReads: z.number(),
    runtimeHashReads: z.number(),
    runtimeHashBytes: z.number().default(0),
    runtimePathReads: z.number().default(0),
    runtimeDirectoryReads: z.number().default(0),
    outputHashReads: z.number(),
    nativeMetadataBytes: z.number(),
    childRenders: z.number(),

    fullWalks: z.number(),
    sourceHashReads: z.number(),
    sourceHashBytes: z.number().optional(),
    resourceHashReads: z.number(),
    nativeResourceHashReads: z.number(),
  }),
});

export { stateSchema };
