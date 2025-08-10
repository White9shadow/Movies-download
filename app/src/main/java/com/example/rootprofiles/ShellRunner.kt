package com.example.rootprofiles

import com.topjohnwu.superuser.Shell

object ShellRunner {
    fun isRootAvailable(): Boolean = Shell.getShell().isRoot

    fun run(commands: List<String>): List<String> {
        val out = mutableListOf<String>()
        val job = Shell.cmd(commands).to(out).submit()
        job.await()
        return out
    }
}
