package com.example.searchgithub

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.repository.GitHubApi
import com.example.searchgithub.repository.GitHubRepository
import com.example.searchgithub.rule.TestCoroutineRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CancellationException
//import kotlinx.coroutines.ChildHandle
//import kotlinx.coroutines.ChildJob
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.selects.SelectClause0
import kotlinx.coroutines.selects.SelectClause1
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import kotlin.coroutines.CoroutineContext

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class GitHubRepositoryTest {

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = GitHubRepository(gitHubApi)
    }

//    @Test
//    fun searchGithub_Test() {
//        testCoroutineRule.runBlockingTest{
//            Mockito.`when`(gitHubApi.searchGithubAsync(SEARCH_TEXT)).thenReturn(
//                object :Deferred<SearchResponse>{
//                    override val children: Sequence<Job>
//                        get() = TODO("Not yet implemented")
//                    override val isActive: Boolean
//                        get() = TODO("Not yet implemented")
//                    override val isCancelled: Boolean
//                        get() = TODO("Not yet implemented")
//                    override val isCompleted: Boolean
//                        get() = TODO("Not yet implemented")
//                    override val key: CoroutineContext.Key<*>
//                        get() = TODO("Not yet implemented")
//                    override val onAwait: SelectClause1<SearchResponse>
//                        get() = TODO("Not yet implemented")
//                    override val onJoin: SelectClause0
//                        get() = TODO("Not yet implemented")
//
//                    @InternalCoroutinesApi
//                    override fun attachChild(child: ChildJob): ChildHandle {
//                        TODO("Not yet implemented")
//                    }
//
//                    override suspend fun await(): SearchResponse {
//                        return SearchResponse(TOTAL_COUNT_TEST, listOf())
//                    }
//
//                    override fun cancel(cause: Throwable?): Boolean {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun cancel(cause: CancellationException?) {
//                        TODO("Not yet implemented")
//                    }
//
//                    @InternalCoroutinesApi
//                    override fun getCancellationException(): CancellationException {
//                        TODO("Not yet implemented")
//                    }
//
//                    @ExperimentalCoroutinesApi
//                    override fun getCompleted(): SearchResponse {
//                        TODO("Not yet implemented")
//                    }
//
//                    @ExperimentalCoroutinesApi
//                    override fun getCompletionExceptionOrNull(): Throwable? {
//                        TODO("Not yet implemented")
//                    }
//
//                    @InternalCoroutinesApi
//                    override fun invokeOnCompletion(
//                        onCancelling: Boolean,
//                        invokeImmediately: Boolean,
//                        handler: CompletionHandler
//                    ): DisposableHandle {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun invokeOnCompletion(handler: CompletionHandler): DisposableHandle {
//                        TODO("Not yet implemented")
//                    }
//
//                    override suspend fun join() {
//                        TODO("Not yet implemented")
//                    }
//
//                    override fun start(): Boolean {
//                        TODO("Not yet implemented")
//                    }
//
//                }
//            )
//
//            assertEquals(repository.searchGithub(SEARCH_TEXT), SearchResponse(TOTAL_COUNT_TEST, listOf()))
//
//        }
//    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
    }

}