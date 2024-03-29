package com.tcn.cosmoslibrary.common.nbt;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.annotation.Nullable;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.StreamTagVisitor;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagType;
import net.minecraft.nbt.TagTypes;

public class CosmosNBTIOHandler {

	public static void write(CompoundTag p_128956_, File p_128957_) throws IOException {
		FileOutputStream fileoutputstream = new FileOutputStream(p_128957_);

		try {
			DataOutputStream dataoutputstream = new DataOutputStream(fileoutputstream);

			try {
				write(p_128956_, dataoutputstream);
			} catch (Throwable throwable2) {
				try {
					dataoutputstream.close();
				} catch (Throwable throwable1) {
					throwable2.addSuppressed(throwable1);
				}

				throw throwable2;
			}

			dataoutputstream.close();
		} catch (Throwable throwable3) {
			try {
				fileoutputstream.close();
			} catch (Throwable throwable) {
				throwable3.addSuppressed(throwable);
			}

			throw throwable3;
		}

		fileoutputstream.close();
	}

	@Nullable
	public static CompoundTag read(File p_128954_) throws IOException {
		if (!p_128954_.exists()) {
			return null;
		} else {
			FileInputStream fileinputstream = new FileInputStream(p_128954_);

			CompoundTag compoundtag;
			try {
				DataInputStream datainputstream = new DataInputStream(fileinputstream);

				try {
					compoundtag = read(datainputstream, NbtAccounter.UNLIMITED);
				} catch (Throwable throwable2) {
					try {
						datainputstream.close();
					} catch (Throwable throwable1) {
						throwable2.addSuppressed(throwable1);
					}

					throw throwable2;
				}

				datainputstream.close();
			} catch (Throwable throwable3) {
				try {
					fileinputstream.close();
				} catch (Throwable throwable) {
					throwable3.addSuppressed(throwable);
				}

				throw throwable3;
			}

			fileinputstream.close();
			return compoundtag;
		}
	}

	public static CompoundTag read(DataInput p_128929_) throws IOException {
		return read(p_128929_, NbtAccounter.UNLIMITED);
	}

	public static CompoundTag read(DataInput p_128935_, NbtAccounter p_128936_) throws IOException {
		Tag tag = readUnnamedTag(p_128935_, 0, p_128936_);
		if (tag instanceof CompoundTag) {
			return (CompoundTag) tag;
		} else {
			throw new IOException("Root tag must be a named compound tag");
		}
	}

	public static void write(CompoundTag p_128942_, DataOutput p_128943_) throws IOException {
		writeUnnamedTag(p_128942_, p_128943_);
	}

	public static void parse(DataInput p_197510_, StreamTagVisitor p_197511_) throws IOException {
		TagType<?> tagtype = TagTypes.getType(p_197510_.readByte());
		if (tagtype == EndTag.TYPE) {
			if (p_197511_.visitRootEntry(EndTag.TYPE) == StreamTagVisitor.ValueResult.CONTINUE) {
				p_197511_.visitEnd();
			}

		} else {
			switch (p_197511_.visitRootEntry(tagtype)) {
			case HALT:
			default:
				break;
			case BREAK:
				StringTag.skipString(p_197510_);
				tagtype.skip(p_197510_);
				break;
			case CONTINUE:
				StringTag.skipString(p_197510_);
				tagtype.parse(p_197510_, p_197511_);
			}

		}
	}

	public static void writeUnnamedTag(Tag p_128951_, DataOutput p_128952_) throws IOException {
		p_128952_.writeByte(p_128951_.getId());
		if (p_128951_.getId() != 0) {
			p_128952_.writeUTF("");
			p_128951_.write(p_128952_);
		}
	}

	private static Tag readUnnamedTag(DataInput p_128931_, int p_128932_, NbtAccounter p_128933_) throws IOException {
		byte b0 = p_128931_.readByte();
		p_128933_.accountBytes(8); // Forge: Count everything!
		if (b0 == 0) {
			return EndTag.INSTANCE;
		} else {
			p_128933_.readUTF(p_128931_.readUTF()); // Forge: Count this string.
			p_128933_.accountBytes(32); // Forge: 4 extra bytes for the object allocation.

			try {
				return TagTypes.getType(b0).load(p_128931_, p_128932_, p_128933_);
			} catch (IOException ioexception) {
				CrashReport crashreport = CrashReport.forThrowable(ioexception, "Loading NBT data");
				CrashReportCategory crashreportcategory = crashreport.addCategory("NBT Tag");
				crashreportcategory.setDetail("Tag type", b0);
				throw new ReportedException(crashreport);
			}
		}
	}
}
