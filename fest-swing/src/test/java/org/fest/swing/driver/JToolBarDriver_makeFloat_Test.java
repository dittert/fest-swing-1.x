/*
 * Created on Feb 25, 2008
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.swing.driver;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.swing.edt.GuiActionRunner.execute;
import static org.fest.swing.test.core.CommonAssertions.failWhenExpectingException;

import java.awt.Window;

import javax.swing.JToolBar;

import org.fest.swing.annotation.RunsInEDT;
import org.fest.swing.edt.GuiTask;
import org.junit.Test;

/**
 * Tests for {@link JToolBarDriver#makeFloat(JToolBar)}.
 * 
 * @author Alex Ruiz
 * @author Yvonne Wang
 */
public class JToolBarDriver_makeFloat_Test extends JToolBarDriver_TestCase {
  @Test
  public void should_throw_error_if_JToolBar_is_not_floatable() {
    makeNotFloatable();
    try {
      driver.makeFloat(toolBar);
      failWhenExpectingException();
    } catch (IllegalStateException e) {
      assertThat(e.getMessage()).contains("is not floatable");
    }
  }

  @RunsInEDT
  private void makeNotFloatable() {
    setFloatable(toolBar, false);
    robot.waitForIdle();
    showWindow();
  }

  @RunsInEDT
  private static void setFloatable(final JToolBar toolBar, final boolean flotable) {
    execute(new GuiTask() {
      @Override
      protected void executeInEDT() {
        toolBar.setFloatable(flotable);
      }
    });
  }

  @RunsInEDT
  public void should_float_JToolbar() {
    Window oldAncestor = toolBarAncestor();
    driver.makeFloat(toolBar);
    Window newAncestor = toolBarAncestor();
    assertThat(newAncestor).isNotSameAs(oldAncestor);
  }
}
