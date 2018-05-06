/*
 * Copyright (C) 2017 Dennis Neufeld
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package space.npstr.wolfia.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by napster on 05.05.17.
 * <p>
 * Provides access to the values of the property file generated by whatever git info plugin we're using
 */
@Slf4j
public class GitRepoState {

    public static GitRepoState getGitRepositoryState() {
        return GitRepoStateHolder.INSTANCE;
    }

    //holder pattern
    private static final class GitRepoStateHolder {
        private static final GitRepoState INSTANCE = new GitRepoState("git.properties");
    }

    public final String branch;
    public final String commitId;
    public final String commitIdAbbrev;
    public final String commitUserName;
    public final String commitUserEmail;
    public final String commitMessageFull;
    public final String commitMessageShort;
    public final long commitTime;

    @SuppressWarnings("ConstantConditions")
    public GitRepoState(final String propsName) {

        final Properties properties = new Properties();
        try {
            properties.load(GitRepoState.class.getClassLoader().getResourceAsStream(propsName));
        } catch (final NullPointerException | IOException e) {
            log.info("Failed to load git repo information", e); //need to build with build tool to get them
        }

        this.branch = String.valueOf(properties.getOrDefault("git.branch", ""));
        this.commitId = String.valueOf(properties.getOrDefault("git.commit.id", ""));
        this.commitIdAbbrev = String.valueOf(properties.getOrDefault("git.commit.id.abbrev", ""));
        this.commitUserName = String.valueOf(properties.getOrDefault("git.commit.user.name", ""));
        this.commitUserEmail = String.valueOf(properties.getOrDefault("git.commit.user.email", ""));
        this.commitMessageFull = String.valueOf(properties.getOrDefault("git.commit.message.full", ""));
        this.commitMessageShort = String.valueOf(properties.getOrDefault("git.commit.message.short", ""));
        this.commitTime = Long.parseLong(String.valueOf(properties.getOrDefault("git.commit.time", "0"))); //epoch seconds
    }
}